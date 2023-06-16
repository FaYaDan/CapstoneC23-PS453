import numpy as np 
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report
from sklearn.tree import DecisionTreeClassifier

dataset = pd.read_csv('D:/capstone/final_test.csv')

#Preprocessing 
#Mengecek Missing Value
missing_values = dataset.isnull().sum()
print(missing_values)

#Menghilangkan Missing Value
data = dataset.dropna()

#Mengubah tipe data Age dan Height Menjadi Integer agar bisa diproses
data['height'] = pd.to_numeric(data['height'], downcast='integer', errors='coerce')
data['age'] = pd.to_numeric(data['age'], downcast='integer', errors='coerce')

# Memisahkan variable x dan y
x = data[['age', 'height', 'weight']]
y = data['size']

# Membagi data menjadi set pelatihan dan pengujian
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.30,random_state=42)

print(x_train.shape, x_test.shape)
print(y_train.shape, y_test.shape)

#Klasifikasi Menggunakan Decision tree
# Model training
model = DecisionTreeClassifier(
    criterion='entropy', 
    max_depth=5, 
    min_samples_split=10
)
print(model.fit(x_train, y_train))
y_pred = model.predict(x_test)

# Menghitung akurasi model
accuracy = accuracy_score(y_test, y_pred)
print('Accuracy:', accuracy)

print(classification_report(y_test, y_pred))
print(y_test)

#cobain pake data baru
baru = {'age': [20],'height': [180],'weight': [100]}
frame = pd.DataFrame(data=baru)

# Melakukan prediksi menggunakan model
prediction = frame.apply(lambda x: model.predict([x])[0], axis=1)
print("Ukuran baju:", prediction[0])
