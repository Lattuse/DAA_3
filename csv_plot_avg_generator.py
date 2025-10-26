import pandas as pd
import matplotlib.pyplot as plt

# Path to your benchmark CSV file
csv_path = 'datasets/benchmark.csv'  # update path as needed

# Load the CSV data
df = pd.read_csv(csv_path)

# Group data by GraphID and Algorithm, compute average ExecutionTimeMs
agg = df.groupby(['GraphID', 'Algorithm'], as_index=False)['ExecutionTimeMs'].mean()

# Pivot data so we have GraphID as index and Prim/Kruskal as columns
pivot = agg.pivot(index='GraphID', columns='Algorithm', values='ExecutionTimeMs')

# Plot
plt.figure(figsize=(10,6))
plt.plot(pivot.index, pivot['Prim'], label='Prim Algorithm', marker='o')
plt.plot(pivot.index, pivot['Kruskal'], label='Kruskal Algorithm', marker='x')

plt.xlabel('Graph ID')
plt.ylabel('Average Execution Time (ms)')
plt.title('Comparison of Average Execution Times: Prim vs Kruskal')
plt.legend()
plt.grid(True)
plt.tight_layout()

plt.savefig("datasets/benchmark_comparison.png")
plt.show()
