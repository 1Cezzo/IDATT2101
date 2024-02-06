import time


def recursion_method_1(n, x):
    if n==1:
        return x
    else:
        return x + recursion_method_1(n-1, x)
    
def recursion_method_2(n, x):
    if n==1:
        return x
    if n%2!=0:
        return x + recursion_method_2((n-1)/2, x+x)
    else:
        return recursion_method_2(n/2, x+x)
    

runder = 0
start = time.time()
slutt = time.time()
while True:
    result = recursion_method_1(100, 5)
    slutt = time.time()
    runder = runder + 1
    if slutt - start > 10:
        break

print("Method 1 uses ", ((slutt - start)/runder)*1000, " milliseconds per loop")


runder = 0
start = time.time()
slutt = time.time()
while True:
    result = recursion_method_2(10000000, 5)
    slutt = time.time()
    runder = runder + 1
    if slutt - start > 10:
        break

print("Method 2 uses ", ((slutt - start)/runder)*1000, " milliseconds per loop")