import matplotlib.pyplot as plt
from sklearn.metrics import ConfusionMatrixDisplay, accuracy_score
import joblib
import numpy as np

X_test = np.load('X_test.npy')
y_test = np.load('y_test.npy')
model = joblib.load('svm_model.pkl')

y_pred = model.predict(X_test)

acc = accuracy_score(y_test, y_pred)
print(f"Test Accuracy: {acc:.2f}")

disp = ConfusionMatrixDisplay.from_estimator(model, X_test, y_test)
plt.title("Confusion Matrix - SVM (CK+ Dataset)")
plt.show()
