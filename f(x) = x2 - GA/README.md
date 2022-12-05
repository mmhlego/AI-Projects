# Genetic Algorithm - f(x) = x^2

This is the first of three projects which use [Genetic Algorithm](https://en.wikipedia.org/wiki/Genetic_algorithm) to solve a certain problem.

Our target in this project is to maximize the value of the following function:

$$f(x) = x^2$$

We try to achieve this goal using **Genetic Algorithm** principles like mutation, crossover & etc and record this process.

This project is written in C++ language and no third-party library was used.

## Project Files

`constants.cpp`: This file contains all settings for our genetic algorithm. Here is the list of settings:

-   `chromosome`: Determines the chromosome structure. since we are using 8 bits in our chromosome, the default value is set to `uint8_t` which represents an unsigned 8-bit integer.
-   `Size`: Size of our population. aka number of chromosomes that together fom a population and different operations apply to them, for example crossover, mutation and etc. The default value is set to `10` chromosomes.
-   `MaxIterationCount`: Maximum number of iterations is one of the stopping conditions that stops the algorithm if the current iteration passes that number. The default value is set to `10` iterations.
-   `ChromosomeLength`: Determines length of the chromosome. Since we are using 8 bits to define a chromosome, the default value is set to `8` bits.
-   `MutationPercent`: Probability for a chromosome to mutate and flip on of its bits. value is shown in `X %` format and the default value is set to `20` percent.
-   `ReproduceMethod`: Determines the algorithm to select and reproduce offsprings from the current population. This field can accept the values `0` as [Roulette Wheel Selection](https://www.baeldung.com/cs/genetic-algorithms-roulette-selection) or `1` as [Tournament Selection](https://en.wikipedia.org/wiki/Tournament_selection).
-   `ShowEachStep`: If set to `true` prints the chromosome data table.
-   `TournamentSize`: An integer that shows of the size of the tournament and is used whenever **ReproduceMethod** is set **Tournament Selection** mode. The default value is set to `3`.
-   `ShowCrossovers`: Set to `true` if you want to log each crossover operation. otherwise set to `false`.
-   `ShowMutations`: Set to `true` if you want to log each mutation operation. otherwise set to `false`.

`chromosome.cpp`: Implements all methods related to chromosomes including:

-   `f(x)`: Defines the fitness function and gets a chromosome and returns its fitness. For this example, fitness is calculated with the formula down below:
    $$f(x) = x^2$$
-   `max_chromosome`: Gets two chromosomes and returns the chromosome with greater value.
-   `chromosome_to_string`: Parses the given chromosome to string.

-   `string_to_chromosome`: Parses the given string to a valid chromosome.

-   `crossover`: Get two chromosomes and applies crossover on them.

-   `mutate`: Mutates the given chromosome by flipping a random bit from `1` to `0` or vice versa.

-   `best-chromosome`: Returns the chromosome with the best fitness in a given population.

`selections.cpp`: A selection algorithm is an algorithm to generate new population from the current one. Two selection algorithms are implemented in this file:

-   [Roulette Wheel Selection](https://www.baeldung.com/cs/genetic-algorithms-roulette-selection): In this algorithm, first we calculate the average fitness of our population. Then creates a new mating pool and copies certain amount of each chromosome to it _(amount = fitness(chromosome) / average_fitness)_

    Then algorithm shuffles the mating pool and applies crossover operation on the 1st and 2nd, 3rd and 4th and ... chromosomes and moves the generated offsprings to a new population.

    In the final step the algorithm randomly mutates some of the chromosomes in the new population _(according to **MutationPercent**)_ and returns the new population.

-   [Tournament Selection](https://en.wikipedia.org/wiki/Tournament_selection): This algorithm first creates an empty mating pool for the selected chromosomes and starts filling the pool.

    Filling process is simple. First creates groups of **TournamentSize** chromosomes and copies random chromosomes from the population to it. Then finds the best chromosome (one with the best fitness) among them and copies it to the mating pool. This process executes for **Size** amount of times so that mating pool fills with chromosomes.

    After that like the previous selection, is splits mating pool to pairs and applies crossover on them and copies the offsprings to the new population.

    Finally the algorithm randomly mutates some of the chromosomes in the new population _(according to **MutationPercent**)_ and returns the new population.

`main.cpp`: This is the main entry of our program. This file handles five important tasks:

-   Generating an initial random population
-   Log all the information about the population
-   Generate new population from the previous one
-   Check the stopping conditions if they hold or not
-   Stop the iteration if needed

    Also there is three stopping conditions:

    1. If the current iteration is more than the **maxIterationCount** determined by the user.
    2. If the best of current population is worst than three latest populations
    3. If maximum value for a chromosome is reached _(Maximum value = 65025)_

    If any of these conditions is true, the algorithm will stop.

## Running the project

To run this project first set all your desired settings in the [constants.cpp](./constants.cpp) file.

After that, to run the algorithm with the given settings, run the command according to your operating system:

### Windows

In windows simply execute the following command in the project directory:

```
Run.bat
```

Then the program starts executing and logs all the details in the [out.txt](./out.txt) file.

### Linux

In linux execute the following command in the project directory:

```bash
./Run.sh
```

_Note: If you get the following error while running in linux:_

```
bash: ./Run.bat: Permission denied
```

_simply run this command to solve it:_

```
sudo chmod +x ./Run.sh
```

_This command allows the Run.sh file to be executed by the user._

After executing the command, the algorithms wil starts executing and outputs the results to the [out.txt](./out.txt) file as shown below.

![sample output](./images/Generation.png)
