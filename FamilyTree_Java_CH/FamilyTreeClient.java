/* Connor Hennessey
 * 9/15/19
 * Assignment 1 - Family Tree
 * Organization of Programming Languages
 */

import java.util.*;

public class FamilyTreeClient
{
	public static void main(String[] args)
	{
		ArrayList<String> childList, siblingList, ancestorList, cousinList, unrelatedList;
		ArrayList<person> fTree = new ArrayList<person>();
		FTQueries q = new FTQueries();
		Map<String, person> treeMap = new HashMap<String, person>();
		int count = 0;

		Scanner scan = new Scanner (System.in);
		String[] read = new String[4];
		String question;

		while(scan.hasNext())
		{
			//Reads input and splits it into array where read[0] is the Action and read[1, 2, 3] are either People or Relationships
			question = scan.nextLine();
			read = question.split(" ");

			//Action - E (Marriage or New Child):
			if(read[0].equals("E")) //read[0] = E, read[1] = Parent1, read[2] = Parent2, read[3] = Child
			{
				if(read.length == 4) //For Parents with a Child
				{
					//Check if Parents do not already exist and adds them if they are not
					if(!treeMap.containsKey(read[1]) || !treeMap.containsKey(read[2]))
					{
						//If Parent1 does not already exist, add
						if(!treeMap.containsKey(read[1]))
						{
							treeMap.put(read[1], new person(read[1]));
							fTree.add(treeMap.get(read[1]));
							count++;
						}
						//If Parent2 does not already exist, add
						if(!treeMap.containsKey(read[2]))
						{
							treeMap.put(read[2], new person(read[2]));
							fTree.add(treeMap.get(read[2]));
							count++;
						}
					}
					//If Parents already exist, add Child
					if(treeMap.containsKey(read[1]) && treeMap.containsKey(read[2]))
					{
						treeMap.put(read[3], new person(read[3], treeMap.get(read[1]), treeMap.get(read[2])));
						fTree.add(treeMap.get(read[3]));
						count++;
					}
					//Adds new Child to Children list
					treeMap.get(read[1]).addChildren(treeMap.get(read[3]));
					treeMap.get(read[2]).addChildren(treeMap.get(read[3]));
					//Adds Spouses to Spouse list if not already listed
					if(treeMap.get(read[1]).getSpouse() != treeMap.get(read[2]).getName())
						treeMap.get(read[1]).setSpouse(treeMap.get(read[2]));
					if(treeMap.get(read[2]).getSpouse() != treeMap.get(read[1]).getName())
						treeMap.get(read[2]).setSpouse(treeMap.get(read[1]));
				}

				//Adds just a Couple to Tree
				else if(read.length == 3) //read[0] = E, read[1] = parent1, read[2] = parent2
				{
					//Adds Spouse1
					if(!treeMap.containsKey(read[1]))
					{
						treeMap.put(read[1], new person(read[1]));
						fTree.add(treeMap.get(read[1]));
						count++;
						if(treeMap.get(read[1]).getSpouse() != treeMap.get(read[2]).getName())
							treeMap.get(read[1]).setSpouse(treeMap.get(read[2]));

					}
					//Adds Spouse2
					if(!treeMap.containsKey(read[2]))
					{
						treeMap.put(read[2], new person(read[2]));
						fTree.add(treeMap.get(read[2]));
						count++;
						if(treeMap.get(read[2]).getSpouse() != treeMap.get(read[1]).getName())
							treeMap.get(read[2]).setSpouse(treeMap.get(read[1]));
					}
				}
			}

			//Action - X (Relationship Queries)
			else if(read[0].equals("X")) //read[0] = X, read[1] = <relationship type>, read[2] = person
			{
				System.out.println(question);

				if(read.length == 5) //Cousin degree case that wasn't specified in the original assignment
				{
					if(read[2].equals("cousin"))
					{
						int level = Integer.parseInt(read[3]);
						System.out.println(q.isCousin(treeMap.get(read[1]), treeMap.get(read[4]), fTree, treeMap, level));
					}
				}

				//Checks if Both people are already in the Tree
				if(treeMap.containsKey(read[1]) && treeMap.containsKey(read[3]))
				{
					//Checks if read[1] is child
					if(read[2].equals("child"))
						System.out.println(q.isChild(treeMap.get(read[1]), treeMap.get(read[3])));

						//Checks if read[1] is sibling
					else if(read[2].equals("sibling"))
						System.out.println(q.isSibling(treeMap.get(read[1]), treeMap.get(read[3])));

						//Checks if read[1] is ancestor
					else if(read[2].equals("ancestor"))
						System.out.println(q.isAncestor(treeMap.get(read[1]), treeMap.get(read[3]), fTree, treeMap));

						//Checks if read[1] is cousin
						//else if(read[2].equals("cousin"))
						//	System.out.println(q.isCousin(treeMap.get(read[1]), treeMap.get(read[3]), fTree, treeMap));

						//Checks if read[1] is unrelated
					else if(read[2].equals("unrelated"))
						System.out.println(q.isUnrelated(treeMap.get(read[1]), treeMap.get(read[3]), fTree, treeMap, count));

						//Invalid relationship received
					else
						System.out.println("Not a valid relationship, please enter a valid relationship.");
				}
				//If person is not in the Tree
				else if(!treeMap.containsKey(read[1]) || !treeMap.containsKey(read[3]))
				{
					if(read[2].equals("unrelated"))
						System.out.println("Yes");
					else if(read.length == 5)
						System.out.print("");
					else
						System.out.println("No");
				}
			}

			//Action - W (List all read[1] relationships)
			else if(read[0].equals("W")) //read[0] = W, read[1] = <list relationship type>, read[2] = person
			{
				System.out.println(question);

				if(read.length == 4) //Cousin degree case that wasn't specified in the original assignment
				{
					if(read[1].equals("cousin"))
					{
						int level = Integer.parseInt(read[2]);
						cousinList = q.listCousins(treeMap.get(read[3]), fTree, treeMap, level);
						for(int i = 0; i < cousinList.size(); i++)
							System.out.println(cousinList.get(i));
					}
				}

				//Checks if person is already in the Tree
				if(treeMap.containsKey(read[2]))
				{
					//Gets a list of all the children of read[2]
					if(read[1].equals("child"))
					{
						childList = q.listChildren(treeMap.get(read[2]), fTree, treeMap);
						for(int i = 0; i < childList.size(); i++)
							System.out.println(childList.get(i));
					}

					//Gets a list of all the siblings of read[2]
					else if(read[1].equals("sibling"))
					{
						siblingList = q.listSiblings(treeMap.get(read[2]), fTree, treeMap);
						for(int i = 0; i < siblingList.size(); i++)
							System.out.println(siblingList.get(i));
					}

					//Gets a list of all the ancestors of read[2]
					else if(read[1].equals("ancestor"))
					{
						ancestorList = q.listAncestors(treeMap.get(read[2]), fTree, treeMap);
						for(int i = 0; i < ancestorList.size(); i++)
							System.out.println(ancestorList.get(i));
					}

					//Gets a list of all the cousins of read[2]
//					else if(read[1].equals("cousin"))
//					{
//						cousinList = q.listCousins(treeMap.get(read[2]), fTree, treeMap);
//						for(int i = 0; i < cousinList.size(); i++)
//							System.out.println(cousinList.get(i));
//					}

					//Gets a list of all the unrelated people of read[2]
					else if(read[1].equals("unrelated"))
					{
						unrelatedList = q.listUnrelated(treeMap.get(read[2]), fTree, treeMap, count);
						for(int i = 0; i < unrelatedList.size(); i++)
							System.out.println(unrelatedList.get(i));
					}
					//If relationship type is not valid
					else
						System.out.println("Not a valid relationship, please enter a valid relationship.");
				}
				//If the person is not in the Tree
				else if(read.length == 4)
					System.out.print("");
				else if(!treeMap.containsKey(read[2]))
					System.out.println("There are no records of that person.");
			}
			//If user did not enter E, X, or W
			else if(read[0] != "E" || read[0] != "X" || read[0] != "W")
				System.out.println("Please enter valid input.");
		}

	}
}


