import sys


# ====================================== Classes


class myQueue:
    data = []

    def push(self, val):
        self.data.append(val)

    def pop(self):
        val = self.data[0]
        self.data = self.data[1:]
        return val

    def isEmpty(self):
        return len(self.data) == 0


class myStack:
    data = []

    def push(self, val):
        self.data.append(val)

    def pop(self):
        val = self.data[-1]
        self.data = self.data[0:len(self.data)-1]
        return val

    def isEmpty(self):
        return len(self.data) == 0


# ====================================== Number of node
node_count = 10
start_node = 1
target_node = -1


def BFS(adj):
    frontier = myQueue()
    ans = []
    visited = []
    frontier.push(start_node)

    while not frontier.isEmpty():
        node = frontier.pop()
        ans.append(node)
        visited.append(node)

        if node == target_node:
            break

        for i in range(node_count):
            if adj[node-1][i] and (i+1 not in visited):
                frontier.push(i+1)
                visited.append(i+1)

    print("BFS result: ", end="")
    print(', '.join([str(i) for i in ans]))


def DFS(adj):
    frontier = myStack()
    ans = []
    visited = []
    frontier.push(start_node)

    while not frontier.isEmpty():
        node = frontier.pop()
        ans.append(node)
        visited.append(node)

        if node == target_node:
            break

        for i in range(node_count):
            if adj[node-1][i] and (i+1 not in visited):
                frontier.push(i+1)
                visited.append(i+1)

    print("DFS result: ", end="")
    print(', '.join([str(i) for i in ans]))


adj = [[False for _ in range(node_count)] for _ in range(node_count)]

# Read data
file = open("./test.txt")
for row in file:
    v, w = [int(i) for i in row.split()]

    if v > node_count or w > node_count:
        print("invalid node value")
        sys.exit(0)

    adj[v-1][w-1] = True
    adj[w-1][v-1] = True

start_node = int(sys.argv[1])
target_node = int(sys.argv[2])

print("Python Results:")
BFS(adj)
DFS(adj)
print("="*50)
