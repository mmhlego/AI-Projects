#include <bits/stdc++.h>
#include "constants.cpp"
using namespace std;

int f(chromosome x) { return x * x; }

chromosome max_chromosome(chromosome a, chromosome b) { return a > b ? a : b; }

string chromosome_to_string(chromosome x) {
	string	ans = "";

	for (int i = 0;i < 8;i++) {
		ans = ((x % 2) ? '1' : '0') + ans;
		x /= 2;
	}

	return ans;
}

chromosome string_to_chromosome(string str) {
	chromosome ans = 0;

	for (char c : str) {
		ans *= 2;
		ans += (c - 48);
	}

	return ans;
}

void crossover(chromosome parent1, chromosome parent2, chromosome* offstring1, chromosome* offstring2) {
	int crossover_point = rand() % (ChromosomeLength - 1) + 1;

	string str1 = chromosome_to_string(parent1);
	string str2 = chromosome_to_string(parent2);

	string str3 = str1.substr(0, crossover_point) + str2.substr(crossover_point, ChromosomeLength);
	string str4 = str2.substr(0, crossover_point) + str1.substr(crossover_point, ChromosomeLength);

	*offstring1 = string_to_chromosome(str3);
	*offstring2 = string_to_chromosome(str4);

	if (ShowCrossovers) {
		cout << "---------------------- Crossover -----------------------" << endl;
		cout << "Crossover point: " << crossover_point << endl;
		cout << chromosome_to_string(parent1) << "(" << (int)parent1 << ") => " << chromosome_to_string(*offstring1) << endl;
		cout << chromosome_to_string(parent2) << "(" << (int)parent2 << ") => " << chromosome_to_string(*offstring2) << endl;
	}

	// *offstring1 = ((parent1 / (1 << crossover_point)) << crossover_point) + parent2 % (1 << crossover_point);
	// *offstring2 = ((parent2 / (1 << crossover_point)) << crossover_point) + parent1 % (1 << crossover_point);
}

void mutate(chromosome* ch) {
	int mutation_point = rand() % ChromosomeLength;

	string str = chromosome_to_string(*ch);
	str[mutation_point] = (str[mutation_point] == '0') ? '1' : '0';

	if (ShowMutations) {
		cout << "---------------------- Mutation ------------------------" << endl;
		cout << "Mutation Happened" << " (" << chromosome_to_string(*ch) << " => " << str << ")" << endl;
	}

	*ch = string_to_chromosome(str);
}

chromosome best_chromosome(chromosome population[Size]) {
	int max_f = 0;
	chromosome best = 0;

	for (int i = 0;i < Size;i++) {
		int f_x = f(population[i]);
		if (f_x > max_f) {
			max_f = f_x;
			best = population[i];
		}
	}

	return best;
}
