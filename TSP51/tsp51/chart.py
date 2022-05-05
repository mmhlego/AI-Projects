import matplotlib.pyplot as plt

Average = []
Best = []
Values = []


with open("values.txt") as file:
    generation = 1
    for data in file.readlines():
        data = data.split()
        Average.append(float(data[0]))
        Best.append(int(data[1]))
        Values.append(generation)
        generation += 1

plt.plot(Values, Average, color='red', marker='')
plt.plot(Values, Best, color='blue', marker='')

plt.xlabel('Generation', fontsize=10)
plt.ylabel('Fitness', fontsize=10)

# plt.xlim(0, Values[-1])
plt.ylim(0, Average[0]+25)

plt.title('TSP51 - Genetic Algorithm', fontsize=14)
plt.grid(False)
plt.show()
