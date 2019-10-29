print("Enter number of nodes")
no_of_nodes = int(input())

pr = [0.0 for i in range(0,no_of_nodes)]

no_of_outbound_links = [0 for i in range(0,no_of_nodes)]

# if inbound_links[0] = [2,3,8], then that means node 0 has incoming link from node 2, 3, and 8
inbound_links = [[] for i in range(0,no_of_nodes)]

print("Enter no of links")
no_of_links = int(input())
for i in range(0,no_of_links):
    print("----- Enter Link ", i, " -----")
    print("Enter source node from 0 to ", no_of_nodes-1)
    source = int(input())
    print("Enter destination node from 0 to ", no_of_nodes-1)
    destination = int(input())
    no_of_outbound_links[source] += 1
    inbound_links[destination].append(source)

print("Enter no of iterations to perform")
iterations = int(input())

d = 0.85
for i in range(0, iterations):
    for j in range(0,no_of_nodes):
        incoming_juice = 0
        for node in inbound_links[j]:
            incoming_juice += pr[node]/no_of_outbound_links[node]

        pr[j] = (1-d) + (d * incoming_juice)


print("Page Rank After ", iterations, " iteration: ")
print(pr)

