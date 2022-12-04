#include <bits/stdc++.h>
#include "chromosome.cpp"
using namespace std;

// ============================================================================== help functions

vector<chromosome> shuffle_population(vector<chromosome> population) {
	vector<chromosome> temp_population;

	for (int i = 0;i < Size;i++) {
		int index = rand() % population.size();
		temp_population.push_back(population[index]);
		population.erase(population.begin() + index);
	}

	return temp_population;
}

vector<chromosome> fill_mating_pool(vector<chromosome> mating_pool) {
	if (mating_pool.size() < Size) {
		for (int i = mating_pool.size();i < Size;i++) {
			mating_pool.push_back(mating_pool[rand() % mating_pool.size()]);
		}
	}

	return mating_pool;
}

bool all_is_same(chromosome population[Size]) {
	for (int i = 1;i < Size;i++)if (population[i] != population[0]) return false;
	return true;
}

// ============================================================================== main selection functions

void roulette_wheel_selection(chromosome population[Size]) {
	int sum = 0;
	for (int i = 0;i < Size;i++) {
		chromosome x = population[i];
		sum += x * x;
	}

	int average = sum / Size;
	vector<chromosome> mating_pool;

	for (int i = 0;i < Size;i++) {
		chromosome x = population[i];
		int count = (int)round(double(f(x)) / double(average));

		for (int j = 0;j < count;j++) { mating_pool.push_back(x); }
	}

	mating_pool = fill_mating_pool(mating_pool);
	mating_pool = shuffle_population(mating_pool);

	for (int i = 0;i < Size;i += 2) {
		crossover(mating_pool[i], mating_pool[i + 1], &population[i], &population[i + 1]);
	}

	for (int i = 0;i < Size;i++) {
		if (rand() % 100 < MutationPercent) {
			mutate(&population[i]);
		}
	}
}

void tournament_selection(chromosome population[Size]) {
	vector<chromosome> mating_pool;

	for (int i = 0;i < Size;i++) {
		chromosome best;
		int best_fitness = 0;

		for (int j = 0;j < TournamentSize;j++) {
			chromosome x = population[rand() % Size];
			if (f(x) > best_fitness) {
				best_fitness = f(x);
				best = x;
			}
		}

		mating_pool.push_back(best);
	}

	for (int i = 0;i < Size;i++) {
		if (rand() % 100 < MutationPercent) {
			mutate(&population[i]);
		} else if (i < Size - 1) {
			crossover(mating_pool[i], mating_pool[i + 1], &population[i], &population[i + 1]);
			i++;
		}
	}

	// if (all_is_same(population)) mutate(&population[rand() % Size]);
}
