import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# ✅ Load CSV
csv_file = 'solver_performance_regular.csv'
df = pd.read_csv(csv_file)

# ✅ Convert numeric columns (handle N/A gracefully)
df['Time(ms)'] = pd.to_numeric(df['Time(ms)'], errors='coerce')
df['Steps'] = pd.to_numeric(df['Steps'], errors='coerce')
df['NodesExpanded'] = pd.to_numeric(df['NodesExpanded'], errors='coerce')

# ✅ Drop rows with missing numeric data
df_clean = df.dropna(subset=['Time(ms)', 'Steps', 'NodesExpanded'])

# ✅ Group by Difficulty + Solver → get mean
summary = df_clean.groupby(['Difficulty', 'Solver']).agg({
    'Time(ms)': 'mean',
    'Steps': 'mean',
    'NodesExpanded': 'mean'
}).reset_index()

sns.set(style='whitegrid')

def plot_metric_simple(summary, metric_mean, ylabel, filename_base):
    # --- Combined chart ---
    plt.figure(figsize=(12, 6))
    for solver in summary['Solver'].unique():
        data = summary[summary['Solver'] == solver]
        plt.plot(data['Difficulty'], data[metric_mean], label=solver, marker='o')
    plt.xlabel('Difficulty')
    plt.ylabel(ylabel)
    plt.yscale('log')  # Log scale for better visibility
    plt.title(f'{ylabel} vs Difficulty (All Solvers)')
    plt.legend()
    plt.tight_layout()
    plt.savefig(f'{filename_base}_combined.png')
    plt.show()

    # --- Separate charts ---
    for solver in summary['Solver'].unique():
        data = summary[summary['Solver'] == solver]
        plt.figure(figsize=(12, 6))
        plt.plot(data['Difficulty'], data[metric_mean], label=solver, marker='o')
        plt.xlabel('Difficulty')
        plt.ylabel(ylabel)
        plt.title(f'{ylabel} vs Difficulty ({solver})')
        plt.legend()
        plt.tight_layout()
        safe_solver_name = solver.replace(" ", "_").replace("*", "")
        plt.savefig(f'{filename_base}_{safe_solver_name}.png')
        plt.show()

# ✅ Generate plots
plot_metric_simple(summary, 'Time(ms)', 'Time (ms)', 'time_vs_difficulty')
plot_metric_simple(summary, 'Steps', 'Steps', 'steps_vs_difficulty')
plot_metric_simple(summary, 'NodesExpanded', 'Nodes Expanded', 'nodes_expanded_vs_difficulty')
