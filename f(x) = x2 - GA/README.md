# Genetic Algorithm - f(x) = x^2

This is the first of three projects which use [Genetic Algorithm](https://en.wikipedia.org/wiki/Genetic_algorithm) to solve a certain problem.

Our target in this project is to maximize the value of the following function:

$$f(x) = x^2$$

We try to achieve this goal using **Genetic Algorithm** principles like mutation, crossover & etc and record this process.

This project is written in C++ language and no third-party library was used.

<hr>

## Project Files

`constants.cpp`: This file contains all settings for our genetic algorithm. Here is the list of settings:

-   **chromosome**: Determines the chromosome structure. since we are using 8 bits in our chromosome, the default value is set to `uint8_t` which represents an unsigned 8-bit integer.
-   **Size**: Size of our population. aka number of chromosomes that together fom a population and different operations apply to them, for example crossover, mutation and etc. The default value is set to `10`.
-   **MaxIterationCount**: Maximum number of iterations is one of the stopping conditions that stops the algorithm if the current iteration passes that number. The default value is set to `10`.
-   **ChromosomeLength**: Determines length of the chromosome. Since we are using 8 bits to define a chromosome, the default value is set to `8`.
-   **MutationPercent**: Probability for a chromosome to mutate and flip on of its bits. value is shown in `X %` format and the default value is set to `20`.
-   **ReproduceMethod**: Determines the algorithm to select and reproduce offsprings from the current population. This field can accept the values `0` as [Roulette Wheel Selection](https://www.baeldung.com/cs/genetic-algorithms-roulette-selection) or `1` as [Tournament Selection](https://en.wikipedia.org/wiki/Tournament_selection).
-   **ShowEachStep**: If set to `true` prints the chromosome data table.
-   **TournamentSize**: An integer that shows of the size of the tournament in **Tournament Selection** and is used whenever **ReproduceMethod** is set to `1`.
-   **ShowCrossovers**: Set to `true` if you want to log each crossover operation. otherwise set to `false`.
-   **ShowMutations**: Set to `true` if you want to log each mutation operation. otherwise set to `false`.

`chromosome.cpp`: Implements all methods related to chromosomes including:

-   `f(x)`: Defines the fitness function and gets a chromosome and returns its fitness. For this example, fitness is calculated with the formula down below:
    $$f(x) = x^2$$
-   `max_chromosome`: Gets two chromosomes and returns the chromosome with greater value.
-   `chromosome_to_string`: Parses the given chromosome to string.

-   `string_to_chromosome`: Parses the given string to a valid chromosome.

-   `crossover`: Get two chromosomes and applies crossover on them.

-   `mutate`: Mutates the given chromosome by flipping a random bit from `1` to `0` or vice versa.

-   `best-chromosome`: Returns the chromosome with the best fitness in a given population.

`selections.cpp`: ...

`main.cpp`: ...
