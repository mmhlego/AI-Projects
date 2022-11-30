import matplotlib.pyplot as plt

Average = []
Best = []
Values = []


with open("values.txt") as file:
    generation = 1
    for data in file.readlines():
        data = data.split()
        Average.append(float(data[0]))
        Best.append(float(data[1]))
        Values.append(generation)
        generation += 1

plt.plot(Values, Average, color='red', marker='', label='Average')
plt.plot(Values, Best, color='blue', marker='', label='Best')

plt.xlabel('Generation', fontsize=10)
plt.ylabel('Fitness', fontsize=10)

plt.legend(loc="upper right")

# plt.xlim(0, Values[-1])
plt.ylim(0, Average[0]+25)

plt.title('N-queens - Genetic Algorithm', fontsize=14)
plt.grid(False)
plt.show()
