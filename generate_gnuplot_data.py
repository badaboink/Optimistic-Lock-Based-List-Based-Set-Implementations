import os

# get all algorithms results paths

input_path = 'results'

# choose to use selected file names or all the benchmark results in the directory

# file_names = ['CoarseGrainedListBasedSet.txt', 'LazyLinkedListSortedSet.txt', 'HandOverHandListBasedSet2_atomic.txt']
file_names = os.listdir(input_path)

file_paths = [os.path.join(input_path,fn) for fn in file_names]

# get throughput for each configuration, for every algorithm
# results = {algorithm.txt : [{'thread_number': '12', 'update_ratio': '10', 'list_size': '10', 'throughput': '2.1'}]}

results = {}

for path, fn in zip(file_paths, file_names):
    results[fn] = []
    
    with open(path) as f:      
        for line in f.readlines():    
            line_split = line.split()

            if line.startswith('Benchmark parameters'):
                results[fn].append({})
            elif line.startswith('  Number of threads:'):
                results[fn][-1]['thread_number'] = line_split[-1]
            elif line.startswith('  Write ratio:'):
                results[fn][-1]['update_ratio'] = line_split[-2]
            elif line.startswith('  Size:'):
                results[fn][-1]['list_size'] = line_split[-2]
            elif line_split and line_split[0] == 'Throughput':
                results[fn][-1]['throughput'] = line_split[-1]

# generate a file for each algorithm, for each plot

output_path = 'gnuplot_data'

plots = {'10_update_ratio-varying_list_sizes' : ['10', None],
        'varying_update_ratios-100_list_size' : [None, '100'], 
        '10_update_ratio-1000_list_size' : ['10', '1000']}
    
for dir, (update_ratio, list_size) in plots.items():
    for algorithm, results_list in results.items():
        os.makedirs(os.path.join(output_path, dir), exist_ok=True)
        
        with open(os.path.join(output_path, dir, algorithm), "w") as f:
            f.write(f'# list_size={list_size} update_ratio={update_ratio}\n')
            f.write(f'thread_number update_ratio list_size throughput\n')
            for result in results_list:
                condition1 = True if list_size == None else result['list_size'] == list_size
                condition2 = True if update_ratio == None else result['update_ratio'] == update_ratio
                if condition1 and condition2:
                    f.write(f"{result['thread_number']} {result['update_ratio']} {result['list_size']} {result['throughput']}\n")