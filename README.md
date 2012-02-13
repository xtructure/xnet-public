**XNet** is a suite of Java software tools developed for creating discrete event simulations and running evolutionary algorithms. While these tools are designed to work well together, they are split into distinct packages so that they may also be used independently. The two main packages are:

* **xsim**
A discrete event simulation library. Simulations are modeled as a set of Components that pass state to one another and update themselves on command according to the phases of a defined Clock.

* **xevolution**
An evolutionary algorithm library. Evolution is modeled as a set of Populations made up of individual Genomes that are transformed, evaluated, and culled from one generation to the next.

Other **xnet** packages either provide supporting functionality or specialized extensions which are useful in particular fields. For instance, there is an **xart** package that provides an implementation of an ART3 network for use with **xsim**, and an **xneat** package that provides an implementation of the NEAT evolutionary algorithm for use with **xevolution**. The **xbatch** package permits xsim simulations to be run in batches, useful for evaluating the simulation performance of phenotypes for fitness purposes during evolution.
