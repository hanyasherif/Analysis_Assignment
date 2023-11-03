import time
import matplotlib.pyplot as plt

def power1(a, n):
    result = 1
    for _ in range(n):
        result *= a
    return result

def power2(a, n):
    if n == 0:
        return 1
    else:
        z = power2(a, n // 2)
        if n % 2 == 0:
            return z * z
        else:
            return a * z * z

exponents = list(range(1, 1000,50))
results_iterative = []
results_divide_conquer = []

for exponent in exponents:
    base = 100  
    start_time = time.time()
    power1(base, exponent)
    end_time = time.time()
    time_iterative = end_time - start_time
    results_iterative.append(time_iterative)

    start_time = time.time()
    power2(base, exponent)
    end_time = time.time()
    time_divide_conquer = end_time - start_time
    results_divide_conquer.append(time_divide_conquer)



plt.plot(exponents, results_iterative, label='Iterative')
plt.plot(exponents, results_divide_conquer, label='Divide & Conquer')
plt.xlabel('Exponent')
plt.ylabel('Time (seconds)')
plt.legend()
plt.show()