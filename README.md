# Collektive Examples

This repository contains the code for the examples of the [Collektive](https://github.com/Collektive/collektive) DSL and STDLIB.

## How to run the examples

To run the examples, you need to clone this repository on your pc, moving into the root folder and running the following command:

```bash
./gradlew run<ExampleName>Batch
```

Where `<ExampleName>` is the name of the example you want to run in batch mode(FieldEvolution, NeighborCounter, Branching, Gradient, MaxID or ChannelWithObstacles).

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
Where `<ExampleName>` is the name of the example you want to run (FieldEvolution, NeighborCounter, Branching, Gradient, MaxID or ChannelWithObstacles).

*Note: to run a program with the "Graphics" tag, it is essential that the environment variable "env:CI" is set to false.*

This variable determines whether the system is operating in a Continuous Integration (CI) environment. If the variable is set to true, certain arguments in the YAML files defined in the build.gradle are overridden. For instance, [in this case](https://github.com/angelacorte/collektive-stdlib-simulations/blob/c0730883e27299c7bb7daa5ea86035c77965bb26/build.gradle.kts#L108), a termination condition for the simulation is added after 2 simulated seconds. 

To configure the environment variable, the following command is used:

#### Linux
```bash
export CI=true
```

#### Windows
```power-shell
$env:CI="true" 
```

