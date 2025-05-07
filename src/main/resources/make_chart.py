import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# === CONFIG ===
CSV_PATH = 'solver_performance_diff15.csv'
OUTPUT_STEPS = 'step_15.png'
OUTPUT_TIME = 'time_15.png'

# === LOAD DATA ===
df = pd.read_csv(CSV_PATH)

# === PLOT STEPS CHART ===
plt.figure(figsize=(10, 5))
for solver in df['Solver'].unique():
    subset = df[df['Solver'] == solver]
    plt.plot(subset['Iteration'], subset['Steps'], label=solver)
plt.title("Steps for Shuffle 15")
plt.xlabel("Iteration")
plt.ylabel("Steps")
plt.xticks(np.arange(1, 11, 1))
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(OUTPUT_STEPS)
plt.close()

# === PLOT TIME CHART ===
plt.figure(figsize=(10, 5))
for solver in df['Solver'].unique():
    subset = df[df['Solver'] == solver]
    plt.plot(subset['Iteration'], subset['Time(ms)'], label=solver)
plt.title("Time (ms) for Shuffle 15")
plt.xlabel("Iteration")
plt.ylabel("Time (ms)")
plt.yscale('log')
plt.xticks(np.arange(1, 11, 1))
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(OUTPUT_TIME)
plt.close()

print(f"✅ Charts saved as '{OUTPUT_STEPS}' and '{OUTPUT_TIME}'")

# === CONFIG ===
CSV_PATH = 'solver_performance_regular.csv' 
OUTPUT_STEPS = 'step_diff.png'
OUTPUT_TIME = 'time_diff.png'

# === LOAD DATA ===
df = pd.read_csv(CSV_PATH)

# === PLOT STEPS CHART ===
plt.figure(figsize=(10, 5))
for solver in df['Solver'].unique():
    subset = df[df['Solver'] == solver]
    plt.plot(subset['Difficulty'], subset['Steps'], label=solver)
plt.title("Steps for Shuffle 15")
plt.xlabel("Difficulty")
plt.ylabel("Steps")
plt.xticks(np.arange(1, 21, 1))
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(OUTPUT_STEPS)
plt.close()

# === PLOT TIME CHART ===
plt.figure(figsize=(10, 5))
for solver in df['Solver'].unique():
    subset = df[df['Solver'] == solver]
    plt.plot(subset['Difficulty'], subset['Time(ms)'], label=solver)
plt.title("Time (ms) for Shuffle 15")
plt.xlabel("Difficulty")
plt.ylabel("Time (ms)")
plt.yscale('log')
plt.xticks(np.arange(1, 21, 1))
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(OUTPUT_TIME)
plt.close()

print(f"✅ Charts saved as '{OUTPUT_STEPS}' and '{OUTPUT_TIME}'")