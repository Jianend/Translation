import numpy as np
import random

h, w = 100, 100
data = np.zeros((h, w), dtype=int)

def next_gen(data):
    new_data = np.zeros((w, h), dtype=int)
    for i in range(w):
        for x in range(h):
            count = np.sum(data[max(0, i-1):min(h, i+2), max(0, x-1):min(w, x+2)]) - data[i, x]
            new_data[i, x] = count == 3 or (count == 2 and data[i, x] == 1)
    return new_data

def insert_one_randomly(matrix, num):
    flat_indices = np.random.choice(h * w, num, replace=False)
    matrix.flat[flat_indices] = 1
    return matrix

if __name__=='__main__':
    data = insert_one_randomly(data, int(w * h * 0.5))
    data = next_gen(data)
