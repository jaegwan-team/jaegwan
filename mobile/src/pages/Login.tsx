import React, {useRef, useState} from 'react';
import {
  View,
  Image,
  StyleSheet,
  Dimensions,
  Pressable,
  Modal,
  Platform,
} from 'react-native';
import Video from 'react-native-video';
import WebView, {
  WebViewMessageEvent,
  WebViewNavigation,
} from 'react-native-webview';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {useNavigation} from '@react-navigation/native';
import {NativeStackNavigationProp} from '@react-navigation/native-stack';
import {RootStackParamList} from '../navigation/types';
import {useUser} from '../contexts/userContext';
import {getUserInfo} from '../api/user';

type LoginScreenNavigationProp = NativeStackNavigationProp<
  RootStackParamList,
  'Login'
>;

// 상수 정의
const Logo = require('../assets/jaegwan_logo.png');
const KakaoButton = require('../assets/kakao_login_large_narrow.png');
const BackgroundVideo = require('../assets/background.mp4');
const BACKEND_BASE_URL = 'https://k11a501.p.ssafy.io';
const {width} = Dimensions.get('window');

// 쿠키를 체크하는 자바스크립트
const INJECTED_JAVASCRIPT = `
 (function() {
   function getCookie(name) {
     const value = "; " + document.cookie;
     const parts = value.split("; " + name + "=");
     if (parts.length === 2) return parts.pop().split(";").shift();
   }
   
   const accessToken = getCookie('accessToken');
   const refreshToken = getCookie('refreshToken');
   
   if (accessToken && refreshToken) {
     window.ReactNativeWebView.postMessage(JSON.stringify({
       type: 'COOKIE_FOUND',
       accessToken,
       refreshToken
     }));
   }
 })();
`;

export default function Login() {
  const navigation = useNavigation<LoginScreenNavigationProp>();
  const [isWebViewVisible, setIsWebViewVisible] = useState(false);
  const webViewRef = useRef<WebView | null>(null);
  const {setUser} = useUser();

  const handleKakaoLogin = () => {
    setIsWebViewVisible(true);
  };

  const handleWebViewMessage = (event: WebViewMessageEvent) => {
    try {
      const data = JSON.parse(event.nativeEvent.data);

      if (data.type === 'COOKIE_FOUND') {
        Promise.all([
          AsyncStorage.setItem('accessToken', data.accessToken),
          AsyncStorage.setItem('refreshToken', data.refreshToken),
        ])
          .then(async () => {
            try {
              const userInfo = await getUserInfo();

              await setUser(userInfo.data); // await 추가

              // 모든 작업이 완료된 후에 네비게이션
              setIsWebViewVisible(false);
              setTimeout(() => {
                // setTimeout으로 약간의 딜레이 추가
                navigation.replace('Main');
              }, 100);
            } catch (error) {
              console.error('Error fetching user info:', error);
            }
          })
          .catch(error => {
            console.error('Error saving tokens:', error);
          });
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleNavigationStateChange = (navState: WebViewNavigation) => {
    if (navState.url.includes('/main')) {
      webViewRef.current?.injectJavaScript(INJECTED_JAVASCRIPT);
    }
  };

  return (
    <View style={styles.container}>
      <Video
        source={BackgroundVideo}
        style={styles.backgroundVideo}
        repeat={true}
        resizeMode="cover"
        muted={true}
        rate={1.0}
      />

      <View style={styles.contentContainer}>
        <View style={styles.logoContainer}>
          <Image source={Logo} style={styles.logo} resizeMode="contain" />
        </View>

        <View style={styles.kakaoContainer}>
          <Pressable style={styles.kakaoButton} onPress={handleKakaoLogin}>
            <Image
              source={KakaoButton}
              style={styles.kakaobtn}
              resizeMode="contain"
            />
          </Pressable>
        </View>
      </View>

      <Modal
        visible={isWebViewVisible}
        onRequestClose={() => setIsWebViewVisible(false)}
        animationType="slide">
        <WebView
          ref={webViewRef}
          source={{uri: `${BACKEND_BASE_URL}/api/auth/kakao/login`}}
          onMessage={handleWebViewMessage}
          onNavigationStateChange={handleNavigationStateChange}
          injectedJavaScript={INJECTED_JAVASCRIPT}
          sharedCookiesEnabled={true}
          thirdPartyCookiesEnabled={true}
          style={styles.webView}
          {...(Platform.OS === 'ios' && {
            incognito: true,
          })}
        />
      </Modal>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  backgroundVideo: {
    position: 'absolute',
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
  },
  contentContainer: {
    flex: 1,
    backgroundColor: 'rgba(26, 83, 25, 0.5)',
  },
  logoContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  logo: {
    width: '70%',
    height: '70%',
  },
  kakaoContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  kakaoButton: {
    width: width * 0.8,
    height: '100%',
  },
  kakaobtn: {
    width: '100%',
    height: '100%',
  },
  webView: {
    flex: 1,
  },
});
