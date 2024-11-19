import React, {useCallback, useRef, useState, useEffect} from 'react';
import {
  View,
  StyleSheet,
  Pressable,
  Text,
  ActivityIndicator,
  Alert,
  Linking,
  Image,
} from 'react-native';
import {
  Camera,
  useCameraDevice,
  useCameraPermission,
} from 'react-native-vision-camera';
import {useIsFocused} from '@react-navigation/native';
import {RootStackParamList} from '../../types/Stack';
import {NativeStackNavigationProp} from '@react-navigation/native-stack';
import {useUser} from '../contexts/userContext';
import AsyncStorage from '@react-native-async-storage/async-storage';

type CameraScreenNavigationProp = NativeStackNavigationProp<
  RootStackParamList,
  'Camera'
>;

type CameraScreenProps = {
  navigation: CameraScreenNavigationProp;
};
export default function CameraScreen({navigation}: CameraScreenProps) {
  const {user} = useUser();
  const camera = useRef<Camera>(null);
  const [isLoading, setIsLoading] = useState(false);
  const device = useCameraDevice('back');
  const isFocused = useIsFocused();
  const {hasPermission, requestPermission} = useCameraPermission();
  const [photoPath, setPhotoPath] = useState<string | null>(null);

  // 권한 요청 처리
  useEffect(() => {
    const checkAndRequestPermission = async () => {
      // 권한이 없는 경우
      if (!hasPermission) {
        // 권한 요청
        const granted = await requestPermission();
        if (!granted) {
          // 권한이 거부된 경우
          Alert.alert(
            '카메라 권한 필요',
            '이 기능을 사용하기 위해서는 카메라 권한이 필요합니다. 설정에서 권한을 허용해주세요.',
            [
              {
                text: '취소',
                onPress: () => navigation.goBack(),
                style: 'cancel',
              },
              {
                text: '설정으로 이동',
                onPress: () => {
                  Linking.openSettings();
                  navigation.goBack();
                },
              },
            ],
          );
        }
      }
    };

    checkAndRequestPermission();
  }, [hasPermission, requestPermission, navigation]);

  const takePhoto = useCallback(async () => {
    try {
      const photo = await camera.current?.takePhoto({
        flash: 'off',
      });

      if (photo) {
        setPhotoPath(`file://${photo.path}`);
      }
    } catch (e) {
      Alert.alert('오류', '사진 촬영 중 오류가 발생했습니다.');
    }
  }, []);

  const sendPhoto = useCallback(async () => {
    if (!photoPath || !user?.restaurants?.[0]?.id) {
      Alert.alert('오류', '식당 정보를 찾을 수 없습니다.');
      return;
    }

    try {
      setIsLoading(true);

      const formData = new FormData();
      formData.append('id', user.restaurants[0].id);

      const timestamp = Date.now();
      const fileInfo = {
        uri: photoPath,
        type: 'image/jpeg',
        name: `${timestamp}_receipt.jpg`,
      };

      formData.append('files', fileInfo);

      const url = 'https://k11a501.p.ssafy.io/api/receipt';

      const accessToken = await AsyncStorage.getItem('accessToken');

      // 첫 번째 요청: 이미지 업로드
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'multipart/form-data',
          Authorization: accessToken ?? '',
        },
        body: formData,
      }).catch(error => {
        throw error;
      });

      const responseText = await response.text();

      if (response.ok) {
        // 응답 데이터 파싱
        const responseData = JSON.parse(responseText);
        const s3ImageUrl = responseData.data[0]; // 배열의 첫 번째 항목
        // 두 번째 요청: 이미지 URL 등록
        const imageRegistrationData = {
          restaurantId: user.restaurants[0].id,
          imageUrl: s3ImageUrl,
        };

        const imageRegistrationResponse = await fetch(
          'https://k11a501.p.ssafy.io/api/receipt/image',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              Authorization: accessToken ?? '',
            },
            body: JSON.stringify(imageRegistrationData),
          },
        );

        if (imageRegistrationResponse.ok) {
          Alert.alert('성공', '영수증이 성공적으로 등록되었습니다.');
          navigation.goBack();
        } else {
          throw new Error(
            `이미지 등록 실패: ${imageRegistrationResponse.status}`,
          );
        }
      } else {
        throw new Error(`서버 에러: ${response.status}`);
      }
    } catch (error: unknown) {
      Alert.alert('오류', '영수증 등록 중 문제가 발생했습니다.');
    } finally {
      setIsLoading(false);
    }
  }, [photoPath, user?.restaurants, navigation]);

  const retakePhoto = useCallback(() => {
    setPhotoPath(null);
  }, []);

  // 권한이 없거나 처리 중인 경우
  if (!hasPermission) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color="#1A5319" />
        <Text style={styles.permissionText}>카메라 권한을 확인하는 중...</Text>
      </View>
    );
  }

  // 카메라 디바이스를 찾을 수 없는 경우
  if (device == null) {
    return (
      <View style={styles.centered}>
        <Text style={styles.errorText}>카메라를 찾을 수 없습니다.</Text>
      </View>
    );
  }

  if (photoPath) {
    return (
      <View style={styles.container}>
        <Image source={{uri: photoPath}} style={StyleSheet.absoluteFill} />

        <View style={styles.previewButtonContainer}>
          <Pressable
            style={[styles.previewButton, styles.cancelButton]}
            onPress={retakePhoto}>
            <Text style={styles.previewButtonText}>다시 촬영</Text>
          </Pressable>
          <Pressable
            style={[styles.previewButton, styles.confirmButton]}
            onPress={sendPhoto}
            disabled={isLoading}>
            {isLoading ? (
              <ActivityIndicator color="white" />
            ) : (
              <Text style={styles.previewButtonText}>확인</Text>
            )}
          </Pressable>
        </View>
      </View>
    );
  }

  // 카메라 촬영 화면
  return (
    <View style={styles.container}>
      {device && isFocused && hasPermission && (
        <Camera
          ref={camera}
          style={StyleSheet.absoluteFill}
          device={device}
          isActive={true}
          photo={true}
        />
      )}
      <View style={styles.buttonContainer}>
        <Pressable style={styles.captureButton} onPress={takePhoto}>
          <Text style={styles.captureText}>촬영</Text>
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },
  permissionText: {
    marginTop: 10,
    color: '#666',
    fontSize: 16,
  },
  errorText: {
    color: 'red',
    fontSize: 16,
  },
  buttonContainer: {
    position: 'absolute',
    bottom: 40,
    width: '100%',
    alignItems: 'center',
  },
  captureButton: {
    width: 70,
    height: 70,
    borderRadius: 35,
    backgroundColor: '#1A5319',
    justifyContent: 'center',
    alignItems: 'center',
  },
  captureText: {
    color: 'white',
    fontSize: 16,
  },
  previewButtonContainer: {
    position: 'absolute',
    bottom: 40,
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    paddingHorizontal: 20,
  },
  previewButton: {
    flex: 1,
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 25,
    marginHorizontal: 10,
  },
  cancelButton: {
    backgroundColor: '#666',
  },
  confirmButton: {
    backgroundColor: '#1A5319',
  },
  previewButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
