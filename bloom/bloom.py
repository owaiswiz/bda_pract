# Bloom filter using 2 hash functions and fixed array size of 25

def h1(string):
    total = 0
    for char in string:
        total += (ord(char) * 7) % 47
    return total % 25

def h2(string):
    total = 0
    for char in string:
        total += ord(char) % 71
    return total % 25

array = [False for _ in range(0,25)]

while True:
    print("1. Add Element")
    print("2. Check if Element Exist")
    print("3. Exit\n")

    choice = int(input())
    if choice == 1:
        print("\nEnter String to be inserted: ")
        string = input()
        array[h1(string)] = True
        array[h2(string)] = True
        print("Inserted.\n")
    elif choice == 2:
        print("\nEnter String that you need to check")
        string = input()
        if array[h1(string)] and array[h2(string)]:
            print(string, " might be present")
        else:
            print(string, " is definitely not present")
    else:
        break
