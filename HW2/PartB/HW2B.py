import pydotplus as pydot 
tree = {}
# FILE IS FORMATTED AS    pid, ppid    (which is child, parent)
children = []
parents = []
rootID = {}

with open("partA_outputN4.csv", "r") as file:
    #read in lines and split into elements containing lists
    lines = file.read()
    processes = lines.split()
    
    #CLEAN DATA:
    newProcesses = []
    for row in processes:
        if row.startswith('[') and row.endswith(']'):
            #convert '[str, str]' to [int, int] with eval and add to new list
            rowToList = eval(row)
            newProcesses.append(rowToList)
        else:
            #add pid and ppid to list
            newProcesses.append(row)

    #debugging:
    # print(newProcesses)
    # print("should return 30431: ")
    # print(newProcesses[7][0])

    #FIND ROOT
    rootID = newProcesses[2][1]#make first parent root by default
    tree.update({rootID:{rootID}})
    for i in range(2, len(newProcesses)):
        if newProcesses[i][1] < rootID:
            rootID = newProcesses[i][1]
        
        #ASSIGN PARENTS AND CHILDREN while we're at it
        if newProcesses[i][1] not in parents:
            parents.append(newProcesses[i][1])
            tree.update({newProcesses[i][1]:{}})#added parents to tree
        if newProcesses[i][0] not in children:
            children.append(newProcesses[i][0])

    #debugging:
    # print(newProcesses)
    # print(rootID)
    # print(parents)
    # print(children)
    
    def determine_children(root):
        children = {}
        
        for i in range(2, len(newProcesses)):
            if newProcesses[i][1] == root:
                children[newProcesses[i][0]] = determine_children(newProcesses[i][0])
        return children
        
    #debugging:
    # print("length of parents list:")
    # print(len(parents))
    # print("length of newProcesses list:")
    # print(len(newProcesses))
    # print("tree dictionary:")
    # print(tree)
    # print("parents list:")
    # print(parents)
    # print("newProcesses list:")
    # print(newProcesses)

file.close()

#wagner's code:
print(tree[newProcesses[2][1]])
def draw(parent_name, child_name):
    edge = pydot.Edge(parent_name, child_name)
    graph.add_edge(edge)

def visit(node, parent=None):
    for k,v in node.items():
        if isinstance(v, dict):
            # We start with the root node whose parent is None
            # we don't want to graph the None node
            if parent:
                draw(parent, k)
            visit(v, k)
        else:
            draw(parent, k)
            # drawing the label using a distinct name
            draw(k, k+'_'+v)

# breaks, don't use unless you have pydot:

# graph = pydot.Dot(graph_type='graph')
# visit(menu)
# graph.write_png('example1_graph.png')



# Credits:
# Learning dictionaries: https://www.geeksforgeeks.org/python-dictionary/#
# Learning file reading: https://saturncloud.io/blog/how-to-load-a-text-file-into-python-using-jupyter-anaconda/#:~:text=To%20import%20the%20text%20file,the%20contents%20of%20the%20file.