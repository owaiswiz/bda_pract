array = [1,2,1,3,1,3,4,5,6]
print("Hash function is (A*x + B) % C")
print("Enter value for A:")
a = int(input())
print("Enter value for B:")
b = int(input())
print("Enter value for C:")
c = int(input())

binary_representation = []
for elem in array: 
    binary_representation.append(str(bin((a*elem + b) % c)))

max_trailing_zeroes = 0
for elem in binary_representation:
    #Reverse binary string => 0b1000 becomes 0001b0
    rev = elem[::-1]

    try:
        trailing_zeroes = rev.index("1")

        max_trailing_zeroes = max(trailing_zeroes, max_trailing_zeroes)
        # We have to get number of trailing zeroes
    except Exception as e:
        # We get an exception if element is 0, since there is no 1 in its binary representation -> do nothing
        pass

print("Approx. Distinct Elements: ", pow(2,max_trailing_zeroes))
