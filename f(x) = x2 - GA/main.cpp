#include <bits/stdc++.h>
#include "selections.cpp"
using namespace std;

void print_generation(int generation) {
	cout << "===================== Generation " << generation << " =====================\n\n";
}

void print_data(chromosome population[Size]) {
	cout << "chromosome \t| x \t| f(x) \t\t| f/sum\t| f/ave\t| count" << endl;
	cout << "------------------------------------------------------" << endl;

	int sum = 0;
	for (int i = 0;i < Size;i++) {
		chromosome x = population[i];
		sum += x * x;
	}
	int average = sum / Size;

	for (int i = 0;i < Size;i++) {
		chromosome x = population[i];
		int f_x = f(x);
		printf("%s\t| %d \t| %d  \t| %.3f\t| %.3f\t| %d \n",
			chromosome_to_string(x).c_str(),
			x,
			f_x,
			double(f_x) / double(sum),
			double(f_x) / double(average),
			(int)round(double(f_x) / double(average)));
		fflush(stdout);
	}
	cout << endl;
}

void print_best_value(chromosome population[Size]) {
	chromosome best = best_chromosome(population);
	cout << "Best value: " << chromosome_to_string(best) << "(" << int(best) << ") , f(x) = " << f(best) << endl;
	cout << endl;
}

void generate_new_population(chromosome population[Size]) {
	if (ReproduceMethod == 0) {
		roulette_wheel_selection(population);
	} else {
		tournament_selection(population);
	}
}

int main() {
	int n, t;

	chromosome population[Size];

	srand(time(0));
	for (int i = 0;i < Size;i++) { population[i] = rand() % 256; }

	print_generation(1);
	if (ShowEachStep) print_data(population);
	print_best_value(population);

	int fitnesses[3] = { 0,0,0 };
	for (int iteration = 2;iteration <= MaxIterationCount;iteration++) {
		generate_new_population(population);
		int current_fitness = f(best_chromosome(population));

		if (current_fitness < (fitnesses[0] + fitnesses[1] + fitnesses[2]) / 3) {
			cout << "New fitness: " << current_fitness << " (Value is descending)" << endl;
			cout << "Max fitness achieved: " << max(max(fitnesses[0], fitnesses[1]), fitnesses[2]) << endl;
			cout << "Stopping Program" << endl;
			break;
		}
		fitnesses[0] = fitnesses[1];
		fitnesses[1] = fitnesses[2];
		fitnesses[2] = current_fitness;

		print_generation(iteration);
		if (ShowEachStep) print_data(population);
		print_best_value(population);

		if (current_fitness == 65025) {
			cout << "New fitness: " << current_fitness << " (Max value reached)\nStopping Program" << endl;
			break;
		}
	}
}
