import kivy
import cv2
import mediapipe as mp
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.camera import Camera
from kivy.uix.label import Label
from kivy.clock import Clock
from kivy.core.window import Window
from PIL import Image
import numpy as np

class BodyMeasurementApp(App):
    def build(self):
        layout = BoxLayout(orientation='vertical')
        self.camera = Camera(resolution=(640, 480), size=(Window.width, Window.height // 2))
        self.camera.play = True
        self.label = Label(text='Measurements', size_hint=(1, 0.2))
        layout.add_widget(self.camera)
        layout.add_widget(self.label)
        Clock.schedule_interval(self.measurements, 1.0/30.0)  # Membaca frame setiap 1/30 detik
        return layout
    
    
    def pixels_to_cm(self, pixels):
        faktor_normalisasi = 0.035
        cm = pixels * faktor_normalisasi
        return round(cm, 2)

    def measurements(self, dt):
        frame = self.camera.texture
        if frame is None:
            return

        frame_data = frame.pixels
        image = Image.frombytes(mode="RGBA", size=(frame.width, frame.height), data=frame_data)
        frame = cv2.cvtColor(np.array(image), cv2.COLOR_RGBA2RGB)

        self.process_frame(frame)

    def process_frame(self, frame):
        mp_drawing = mp.solutions.drawing_utils
        mp_pose = mp.solutions.pose


        with mp_pose.Pose(min_detection_confidence=0.5, min_tracking_confidence=0.5) as pose:
            frame.flags.writeable = False
            results = pose.process(frame)

            if results.pose_landmarks:
              
                left_shoulder_x = results.pose_landmarks.landmark[mp_pose.PoseLandmark.LEFT_SHOULDER].x
                right_shoulder_x = results.pose_landmarks.landmark[mp_pose.PoseLandmark.RIGHT_SHOULDER].x
                arm_length = abs(left_shoulder_x - right_shoulder_x) * frame.shape[1]
                arm_lengthCm = self.pixels_to_cm(arm_length)
           
                shoulder_width = abs(left_shoulder_x - right_shoulder_x) * frame.shape[1]
                shoulder_widthCM = self.pixels_to_cm(shoulder_width)
          
                left_hip_x = results.pose_landmarks.landmark[mp_pose.PoseLandmark.LEFT_HIP].x
                right_hip_x = results.pose_landmarks.landmark[mp_pose.PoseLandmark.RIGHT_HIP].x
                body_width = abs(left_hip_x - right_hip_x) * frame.shape[1]
                body_widthCM = self.pixels_to_cm(body_width)

                top_ear_y = results.pose_landmarks.landmark[mp_pose.PoseLandmark.LEFT_EAR].y
                bottom_foot_y = results.pose_landmarks.landmark[mp_pose.PoseLandmark.RIGHT_ANKLE].y
                body_height = abs(top_ear_y - bottom_foot_y) * frame.shape[0]
                body_heightCM = self.pixels_to_cm(body_height)

                
                self.label.text = f'Lengan: {arm_lengthCm} cm\nBahu: {shoulder_widthCM} cm\nLebar Badan: {body_widthCM} cm\nTinggi Badan: {body_heightCM} cm'

            mp_drawing.draw_landmarks(
                frame, results.pose_landmarks, mp_pose.POSE_CONNECTIONS,
                mp_drawing.DrawingSpec(color=(0, 0, 255), thickness=2, circle_radius=2),
                mp_drawing.DrawingSpec(color=(0, 255, 0), thickness=2))

            texture = self.camera.texture
            buffer = frame.tostring()
            texture.blit_buffer(buffer, colorfmt='rgb', bufferfmt='ubyte')

if __name__ == '__main__':
    BodyMeasurementApp().run()
