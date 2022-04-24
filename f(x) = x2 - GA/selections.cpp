#include <bits/stdc++.h>
#include "chromosome.cpp"
using namespace std;

// ============================================================================== help functions

vector<chromosome> shuffle_society(vector<chromosome> society) {
	vector<chromosome> temp_society;

	for (int i = 0;i < Size;i++) {
		int index = rand() % society.size();
		temp_society.push_back(society[index]);
		society.erase(society.begin() + index);
	}

	return temp_society;
}

vector<chromosome> fill_mating_pool(vector<chromosome> mating_pool) {
	if (mating_pool.size() < Size) {
		for (int i = mating_pool.size();i < Size;i++) {
			mating_pool.push_back(mating_pool[rand() % mating_pool.size()]);
		}
	}

	return mating_pool;
}

bool all_is_same(chromosome society[Size]) {
	for (int i = 1;i < Size;i++)if (society[i] != society[0]) return false;
	return true;
}

// ============================================================================== main selection functions

void roulette_wheel_selection(chromosome society[Size]) {
	int sum = 0;
	for (int i = 0;i < Size;i++) {
		chromosome x = society[i];
		sum += x * x;
	}

	int average = sum / Size;
	vector<chromosome> mating_pool;

	for (int i = 0;i < Size;i++) {
		chromosome x = society[i];
		int count = (int)round(double(f(x)) / double(average));

		for (int j = 0;j < count;j++) { mating_pool.push_back(x); }
	}

	mating_pool = fill_mating_pool(mating_pool);
	mating_pool = shuffle_society(mating_pool);

	for (int i = 0;i < Size;i += 2) {
		crossover(mating_pool[i], mating_pool[i + 1], &society[i], &society[i + 1]);
	}

	for (int i = 0;i < Size;i++) {
		if (rand() % 100 < MutationPercent) {
			mutate(&society[i]);
		}
	}
}

void tournament_selection(chromosome society[Size]) {
	vector<chromosome> mating_pool;

	for (int i = 0;i < Size;i++) {
		chromosome best;
		int best_fitness = 0;

		for (int j = 0;j < TournamentSize;j++) {
			chromosome x = society[rand() % Size];
			if (f(x) > best_fitness) {
				best_fitness = f(x);
				best = x;
			}
		}

		mating_pool.push_back(best);
	}

	for (int i = 0;i < Size;i++) {
		if (rand() % 100 < MutationPercent) {
			mutate(&society[i]);
		} else if (i < Size - 1) {
			crossover(mating_pool[i], mating_pool[i + 1], &society[i], &society[i + 1]);
			i++;
		}
	}

	// if (all_is_same(society)) mutate(&society[rand() % Size]);
}
