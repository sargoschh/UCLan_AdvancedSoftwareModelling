# Facial Emotion Recognition (Traditional ML)

This project implements a facial emotion recognition pipeline using classical machine learning.
It compares multiple classifiers on JAFFE and CK+ datasets.

## Features
- Feature extraction: HOG, LBP
- Classifiers: SVM, KNN, Decision Tree, Naive Bayes
- Handles class imbalance with SMOTE
- Produces confusion matrix, accuracy, and F1-score

## How to Run
1. Preprocess dataset using OpenCV (crop faces, resize to 100x100).
2. Run `feature_extraction.py` to generate features.
3. Train model using `train_models.py`.
4. Evaluate results with `evaluate_results.py`.

## Report
See `/report/main.tex` (compile with Overleaf).
