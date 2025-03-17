# Collektive Examples

This repository contains the code for the examples of the [Collektive](https://github.com/Collektive/collektive) DSL and STDLIB.

## How to run the examples natively

To run the examples, you need to clone this repository on your pc, moving into the root folder and running the following command:

```bash
./gradlew run<ExampleName>Batch
```

Where `<ExampleName>` is the name of the example you want to run in batch mode (FieldEvolution, NeighborCounter, Branching, Gradient, MaxID, Exercises or ChannelWithObstacles).

## Running graphical simulations

It is possible to run also the graphical simulations with the [Alchemist simulator](https://alchemistsimulator.github.io).
You can list the available simulations by running the following command:
```bash
./gradlew tasks --all
```
And it will list all the available tasks, including the ones for the graphical simulations in the section "Run Alchemist tasks", or:
```bash
./gradlew run<ExampleName>Graphic
```
Where `<ExampleName>` is the name of the example you want to run (FieldEvolution, NeighborCounter, Branching, Gradient, MaxID, Exercises or ChannelWithObstacles).

*Note: to run a program with the Graphics mode, it is essential that the environment variable `env:CI` is set to false.*

## How to run the examples with containers (not recommended with the graphical simulation)
