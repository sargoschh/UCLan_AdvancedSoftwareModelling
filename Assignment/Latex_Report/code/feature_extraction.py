import cv2
import numpy as np
from skimage.feature import hog, local_binary_pattern

def extract_hog_features(image):
    features, _ = hog(image, orientations=9, pixels_per_cell=(8,8),
                      cells_per_block=(2,2), block_norm='L2-Hys', visualize=True)
    return features

def extract_lbp_features(image):
    lbp = local_binary_pattern(image, P=8, R=1, method="uniform")
    (hist, _) = np.histogram(lbp.ravel(), bins=np.arange(0, 10), range=(0, 9))
    hist = hist.astype("float")
    hist /= hist.sum()
    return hist
