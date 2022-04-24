#include <bits/stdc++.h>
#include "selections.cpp"
using namespace std;

void print_generation(int generation) {
	cout << "===================== Generation " << generation << " =====================\n\n";
}

void print_data(chromosome society[Size]) {
	cout << "chromosome \t| x \t| f(x) \t\t| f/sum\t| f/ave\t| count" << endl;
	cout << "------------------------------------------------------" << endl;

	int sum = 0;
	for (int i = 0;i < Size;i++) {
		chromosome x = society[i];
		sum += x * x;
	}
	int average = sum / Size;

	for (int i = 0;i < Size;i++) {
		chromosome x = society[i];
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

void print_best_value(chromosome society[Size]) {
	chromosome best = best_chromosome(society);
	cout << "Best value: " << chromosome_to_string(best) << "(" << int(best) << ") , f(x) = " << f(best) << endl;
	cout << endl;
}

void generate_new_society(chromosome society[Size]) {
	if (ReproduceMethod == 0) {
		roulette_wheel_selection(society);
	} else {
		tournament_selection(society);
	}
}

int main() {
	int n, t;

	chromosome society[Size];

	srand(time(0));
	for (int i = 0;i < Size;i++) { society[i] = rand() % 256; }

	print_generation(1);
	if (ShowEachStep) print_data(society);
	print_best_value(society);

	int fitnesses[3] = { 0,0,0 };
	for (int iteration = 2;iteration <= MaxIterationCount;iteration++) {
		generate_new_society(society);
		int current_fitness = f(best_chromosome(society));

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
		if (ShowEachStep) print_data(society);
		print_best_value(society);

		if (current_fitness == 65025) {
			cout << "New fitness: " << current_fitness << " (Max value reached)\nStopping Program" << endl;
			break;
		}
	}
}
