# 🌐 Pandemic Simulation Project

## 🔄 Overview

This project explores the mathematical and computational modeling of pandemics using a numerical/statistical approach. It investigates the spread of infectious diseases through simulated frameworks, incorporating parameters such as infection probability, healing probability, and mortality rate. The project evaluates strategies like vaccination and movement restrictions to mitigate the impact of pandemics.

## 👥 Authors and Contributors

### 🔵 Students
- Simone Boscaratto
- Leonardo Breda
- Anna De Biasi
- Francesco Luigi De Faveri
- Bianca Della Libera
- Beatrice Gatti
- Yui Man Kwan
- Marco Micheletto
- Andrea Munarin
- Erica Piccin
- Camilla Viviani

### 🔵 Teachers

- Fabio Breda
- Francesco Maria Cardano
- Francesco Zampieri

### 🎓 Researcher

- Alberto Zanardo, University of Padova, Italy

## 🏫 Institution

I.S.I.S.S. “Marco Casagrande”, Pieve di Soligo, Treviso, Italy

## ❓ Problem Statement

The project models the spread of a disease under the following conditions:

🔹 Each patient can infect another entity with a certain probability.

🔹 Each patient can recover with a certain probability.

🔹 Each patient can die with a certain probability.

The primary objective is to determine the effects of vaccination and isolation strategies in reducing disease impact.

## ⚙️ Approach

### 🔲 Static Framework

The initial framework uses a square matrix to represent the population, with the disease spreading through adjacent cells. Simulations account for:

- ➡️ Transmission routes.
- ➡️ Quarantine.
- ➡️ Limitations on movement.

### 🔲 Dynamic Framework

More realistic simulations include:

🔹 Randomized movement of individuals.

🔹 Different transmission mechanisms (e.g., direct contact, airborne, vector-borne).

🔹 Vaccination strategies.

## 💻 Tools and Methods

- 🔄 Programming Languages: C++ and Java.
- 🔲 Framework Features:
    - Graphical User Interface (GUI) in Java.
    - Simulation of various transmission and vaccination scenarios.
- 🔢 Key Metrics:
    - Infection probability.
    - Mortality rate.
    - Recovery probability.

## 🔝 Results and Key Findings

🔹 Vaccination is critical in achieving herd immunity, with a threshold (HIT) calculated for diseases like measles.

🔹 Introducing movement and varied transmission routes into simulations aligns closely with real-world scenarios.

🔹 Simulation outcomes were validated against historical data for diseases such as Ebola, measles, and influenza.

## 📊 Data Sources

The project references data from:

🔹 Medical literature on disease characteristics, such as R0 (basic reproductive ratio), mortality rates, and duration.

🔹 Online resources, including:

Wikipedia pages on herd immunity, Ebola, SARS, influenza, and other diseases.

## 🚀 Usage

1. 🔧 Install Requirements:
Ensure the necessary software (Java and C++) is installed on your system.
2. 🎮 Run Simulations:
    - Use the C++ program for basic static simulations.
    - Switch to the Java GUI for advanced scenarios involving vaccination and movement.
3. 🗓 Input Parameters:
Adjust infection, recovery, and mortality probabilities based on the disease model.
4. 🔬 Analyze Results:
Extract data on infection spread, mortality, and recovery for different scenarios.

## 🔗 References

Wikipedia: Herd Immunity, Ebola Virus Disease, Severe Acute Respiratory Syndrome, Influenza, etc.

Historical disease data from validated medical literature.

- 📅 Presented at: Berlin, 13/03/2018
- 🔝 Acknowledgment: Math.en.Jeans initiative
