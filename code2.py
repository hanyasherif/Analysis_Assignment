import timeit
import matplotlib.pyplot as plt
import random

def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        left_half = arr[:mid]
        right_half = arr[mid:]

        merge_sort(left_half)
        merge_sort(right_half)

        i = j = k = 0

        while i < len(left_half) and j < len(right_half):
            if left_half[i] < right_half[j]:
                arr[k] = left_half[i]
                i += 1
            else:
                arr[k] = right_half[j]
                j += 1
            k += 1

        while i < len(left_half):
            arr[k] = left_half[i]
            i += 1
            k += 1

        while j < len(right_half):
            arr[k] = right_half[j]
            j += 1
            k += 1

def binary_search(arr, target):
    left, right = 0, len(arr) - 1
    pairs = []

    while left < right:
        current_sum = arr[left] + arr[right]

        if current_sum == target:
            pairs.append((arr[left], arr[right]))
            left += 1
            right -= 1
        elif current_sum < target:
            left += 1
        else:
            right -= 1

    return pairs

def find_pairs_with_sum(S, target):
    merge_sort(S)
    pairs = binary_search(S, target)
    return pairs

S = [4, 3, 2, 1, 5, 6, 7, 8, 9]
target = 10
pairs = find_pairs_with_sum(S, target)
print("Pairs with the sum", target, "are:", pairs)


def measure_execution_time(n):
    S = [random.randint(1, 1000) for _ in range(n)]
    target = random.randint(1, 2000)
    timer = timeit.Timer(lambda: find_pairs_with_sum(S, target))
    execution_time = timer.timeit(number=1)
    return execution_time

input_sizes = [10, 100, 1000, 10000, 100000, 1000000, 10000000]
execution_times = []

for n in input_sizes:
    time = measure_execution_time(n)
    execution_times.append(time)

plt.plot(input_sizes, execution_times, marker='o')
plt.xlabel('Input Size (n)')
plt.grid(True)
plt.show()