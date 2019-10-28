from person import person
from relation import Relation
import sys

queries = Relation()
familyTree = dict()
while True:
    answer = sys.stdin.readline().rstrip('\n\r')
    list = answer.split(" ")
    num = len(list)
    degree = -1
    if num >= 3:
        if list[0] == "E":
            #add to family tree dictionary if not in familytree
            if list[1] not in familyTree:
                familyTree[list[1]] = person(list[1])
            if list[2] not in familyTree:
                familyTree[list[2]] = person(list[2])
            if num == 4:
                if list[3] not in familyTree:
                    #add kid to tree and parents to kid's parent list
                    familyTree[list[3]] = person(list[3])
                    familyTree[list[3]].add_parents(familyTree[list[1]])
                    familyTree[list[3]].add_parents(familyTree[list[2]])

                    #add kids to parents children list
                    familyTree[list[1]].add_children(familyTree[list[3]])
                    familyTree[list[2]].add_children(familyTree[list[3]])

        elif list[0] == 'X' and num == 4:
            if (list[1] in familyTree) and (list[3] in familyTree):
                print(queries.xMethod(familyTree[list[1]], familyTree[list[3]], list[2], degree))
            elif list[2] == 'unrelated':
                print('Yes')
            else:
                print('No')

        elif list[0] == 'W' and num == 3:
            if list[2] in familyTree:
                queryList = (queries.wMethod(familyTree[list[2]], list[1], familyTree, degree))
                for a in queryList:
                    print(a)

        elif num == 5:  # handles cousin degree case - W/X name1 cousin # name2
            degree = list[3]
            if list[0] == 'X' and list[2] == 'cousin':
                if (list[1] in familyTree) and (list[3] in familyTree):
                    print(queries.xMethod(familyTree[list[1]], familyTree[list[3]], list[2], degree))
                elif list[2] == 'unrelated':
                    print('Yes')
                else:
                    print('No')
            elif list[0] == 'W' and list[2] == 'cousin':
                if list[2] in familyTree:
                    queryList = (queries.wMethod(familyTree[list[2]], list[1], familyTree, degree))
                    for a in queryList:
                        print(a)
            else:
                print("Error: cousins not working")

        else:
            print("Error: query doesn't exist")
