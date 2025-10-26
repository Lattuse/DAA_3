import json
import random

def generate_graph(num_nodes, graph_id):
    nodes = [f"Node_{i}" for i in range(1, num_nodes + 1)]
    edges = []


    for i in range(num_nodes):
        connections = random.randint(1, min(5, num_nodes - 1))
        targets = random.sample(nodes, connections)
        for target in targets:
            if nodes[i] != target:
                weight = random.randint(1, 20)
                edges.append({"from": nodes[i], "to": target, "weight": weight})

    return {"id": graph_id, "nodes": nodes, "edges": edges}


def main():
    dataset = {"graphs": []}
    graph_id = 1

    # 5 Small graphs (<30 vertices)
    for _ in range(5):
        dataset["graphs"].append(generate_graph(random.randint(5, 29), graph_id))
        graph_id += 1

    # 10 Medium graphs (<300 vertices)
    for _ in range(10):
        dataset["graphs"].append(generate_graph(random.randint(30, 299), graph_id))
        graph_id += 1

    # 10 Large graphs (<1000 vertices)
    for _ in range(10):
        dataset["graphs"].append(generate_graph(random.randint(300, 999), graph_id))
        graph_id += 1

    # 5 Extra large graphs (<3000 vertices)
    for _ in range(5):
        dataset["graphs"].append(generate_graph(random.randint(1000, 2999), graph_id))
        graph_id += 1

    # Сохранение в JSON файл
    with open("graphs_dataset.json", "w", encoding="utf-8") as f:
        json.dump(dataset, f, indent=2)

    print("Dataset успешно сгенерирован и сохранён в 'graphs_dataset.json'.")


if __name__ == "__main__":
    main()