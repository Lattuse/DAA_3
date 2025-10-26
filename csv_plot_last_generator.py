import pandas as pd
import matplotlib.pyplot as plt

# Load CSV
csv_path = 'datasets/benchmark.csv'  # Change path if needed
df = pd.read_csv(csv_path)

# In case there are multiple runs, keep only last run per GraphID & Algorithm
# Assuming CSV rows are ordered by run time or run number, last appearance = last run
df_last = df.groupby(['GraphID', 'Algorithm']).tail(1)

# Pivot data for plotting
pivot = df_last.pivot(index='GraphID', columns='Algorithm', values='ExecutionTimeMs').reset_index()

# Plot
plt.figure(figsize=(10, 6))
plt.plot(pivot['GraphID'], pivot['Prim'], marker='o', label='Prim Algorithm')
plt.plot(pivot['GraphID'], pivot['Kruskal'], marker='x', label='Kruskal Algorithm')

plt.xlabel('Graph ID')
plt.ylabel('Execution Time (ms) - Last run')
plt.title('Execution Time Comparison of Last Runs of Prim and Kruskal')
plt.grid(True)
plt.legend()
plt.tight_layout()

# Save plot to file
plt.savefig('datasets/benchmark_last_run_comparison.png')

plt.show()
