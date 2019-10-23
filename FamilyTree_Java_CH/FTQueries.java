/* Connor Hennessey
 * 9/15/19
 * Assignment 1 - Family Tree
 * Organization of Programming Languages
 */

import java.util.*;

public class FTQueries
{

// X Queries:

	//Checks if p1 is a Child of p2
	public String isChild(person p1, person p2)
	{
		String output = "No";
		if(p1.getParent1().equals(p2) || p1.getParent2().equals(p2))
			output = "Yes";
		return output;
	}

	//Checks if the p1 is a Sibling of p2
	public String isSibling(person p1, person p2)
	{
		String output = "No";
		if(p1.getParent1().equals(p2.getParent1()) && p1.getParent2().equals(p2.getParent2()))
			output = "Yes";
		if(p1.getParent1().equals(p2.getParent2()) && p1.getParent2().equals(p2.getParent1()))
			output = "Yes";
		return output;
	}

	//Checks if p1 is an Ancestor of p2
	public String isAncestor(person p1, person p2, ArrayList<person> tree, Map<String, person> map)
	{
		String output = "No";
		//Checks if p2 has any Parents
		if(p2.hasParents())
		{
			Queue<person> check = new LinkedList<person>();
			person current;
			boolean checkEnd = false;

			while(checkEnd != true)
			{
				if(p2.getParent1().equals(p1) || p2.getParent2().equals(p1))
				{
					output = "Yes";
					checkEnd = true;
				}
				else
				{
					//Adds Parents of Current Ancestor to the queue
					check.add(map.get(p2.getParent1()));
					check.add(map.get(p2.getParent2()));
					current = map.get(check.poll());

					//Continues checking Parents until no more exist
					while(!check.isEmpty() && current.hasParents())
					{
						if(current.hasParents())
						{
							if (current.getParent1().equals(p1) || current.getParent2().equals(p1))
							{
								output ="Yes";
								checkEnd = true;
							}
							else
							{
								check.add(current.getParent1());
								check.add(current.getParent2());
								current = check.poll();
							}
						}
					}
					if(check.isEmpty() && !current.hasParents())
						checkEnd = true;
				}
			}
		}
		return output;
	}

	//Checks if p1 and p2 are Cousins //Modified for new cousin level parameter
	public String isCousin(person p1, person p2, ArrayList<person> tree, Map<String, person> map, int level)
	{
		String output = "No";
		if(p1.hasParents() && p2.hasParents())
		{
			if(listCousins(p1, tree, map, level).contains(p2))
				output = "Yes";
		}
		return output;

		//Original parts of method before new level parameter
//			if(isAncestor(p1, p2, tree, map).equals("No"))
//			{
//				ArrayList<String> ancestors1, ancestors2;
//				ancestors1 = listAncestors(p1, tree, map, level);
//				ancestors2 = listAncestors(p2, tree, map, level);
//				for(int i = 0; i < ancestors1.size(); i++)
//					for(int j = 0; j < ancestors2.size(); j++)
//						if(ancestors1.get(i).equals(ancestors2.get(j)))
//							output = "Yes";
//			}

	}

	//Checks if p1 and p2 are Unrelated
	public String isUnrelated(person p1, person p2, ArrayList<person> tree, Map<String, person> map, int count)
	{
		String output = "No";
		if(isAncestor(p1, p2, tree, map).equals("No"))
			if(isAncestor(p2, p1, tree, map).equals("No"))
				if(isCousin(p1, p2, tree, map, count).equals("No"))
					output = "Yes";
		return output;
	}



// Q Queries:

	//Returns ArrayList of Children of p
	public ArrayList<String> listChildren(person p, ArrayList<person> tree, Map<String, person> map)
	{
		ArrayList<String> children = new ArrayList<String>();
		ArrayList<person> childrenList = p.getChildren();
		if(map.containsKey(p.getName()))
		{
			for(int i = 0; i < childrenList.size(); i++) //Iterates through the list of Children
				children.add(childrenList.get(i).getName());
		}
		else
			children.add(p.getName() + " has no children on record.");
		Collections.sort(children);
		return children;
	}

	//Returns ArrayList of Siblings of p
	public ArrayList<String> listSiblings(person p, ArrayList<person> tree, Map<String, person> map)
	{
		ArrayList<String> siblings = new ArrayList<String>();
		if(p.hasParents())
		{
			ArrayList<person> siblingsList = p.getParent1().getChildren();
			siblingsList = p.getParent2().getChildren();
			for(int i = 0; siblingsList.size() <= 0; i++)
			{
				String current = siblingsList.get(i).getName();
				siblings.add(current);
			}
		}
		else
			siblings.add(p.getName() + " has no siblings on record.");
		Collections.sort(siblings);
		return siblings;
	}

	//Returns ArrayList of Ancestors of p
	public ArrayList<String> listAncestors(person p, ArrayList<person> tree, Map<String, person> map)
	{
		ArrayList<String> ancestors = new ArrayList<String>();
		ArrayList<person> check = new ArrayList<person>();
		int i = 0;
		if(p.hasParents())
		{
			check.add(p.getParent1());
			check.add(p.getParent2());
			while(!check.isEmpty() && i < check.size())
			{
				person current = check.get(i);
				ancestors.add(current.getName());
				if(current.hasParents())
				{
					if(!check.contains(current.getParent1()))
						check.add(current.getParent1());
					if(!check.contains(current.getParent2()))
						check.add(current.getParent2());
				}
				i++;
			}
			if (ancestors.isEmpty())
				ancestors.add(p.getName() + " has no ancestors on record.");
		}
		else
			ancestors.add(p.getName() + " has no ancestors on record.");
		Collections.sort(ancestors);
		return ancestors;
	}

	//Returns ArrayList of Cousins of p
	public ArrayList<String> listCousins(person p, ArrayList<person> tree, Map<String, person> map, int level)
	{
		ArrayList<String> cousins = new ArrayList<String>();
		ArrayList<String> greatGrandParents = new ArrayList<String>();
		greatGrandParents.addAll(getGreatGrandparents(p.getName(), new ArrayList<String>(), map, level));
		ArrayList<String> L1 = new ArrayList<String>();
		for(int g = 0; g < greatGrandParents.size(); g++)
			L1.addAll(getLvl1(greatGrandParents.get(g), new ArrayList<String>(), map, level)); 
		ArrayList<String> targeted = new ArrayList<String>();
		for(int d = 0; d < L1.size(); d++) 
			if(!getDescendants(L1.get(d), new ArrayList<String>(), map).contains(p))
				targeted.add(L1.get(d)); 
		for(int a = 0; a < targeted.size(); a++)
			cousins.addAll(map.get(targeted.get(a)).getChildrenNames());
		Set<String> set = new HashSet<>(cousins);
		cousins.clear();
		cousins.addAll(set);
		cousins.remove(p.getName());
		for(int x = 0; x < cousins.size(); x++) 
			if(p.getSiblings().contains(cousins.get(x)))
				cousins.remove(x); 
		Collections.sort(cousins);
		return cousins;
	}
	//Returns list of names for descendants at the first level
	public ArrayList<String> getLvl1(String p, ArrayList<String> tree, Map<String, person> map, int level)
	{
		if(map.get(p).getChildren().isEmpty())
			return tree;
		if(level == 1)
		{
			tree.addAll(map.get(p).getChildrenNames());
			return tree;
		}
		for(String desc:map.get(p).getChildrenNames())
			getLvl1(desc, tree, map, level-1);
		return tree;
	}
	//Returns list of the names of great Grandparents
	public ArrayList<String> getGreatGrandparents(String p1, ArrayList<String> t, Map<String, person> map, int level)
	{
		if(!map.get(p1).hasParents())
			return t;
		if(level == 0)
		{
			t.add(map.get(p1).getParent1().getName());
			t.add(map.get(p1).getParent2().getName());
		}
		else
		{
			getGreatGrandparents(map.get(p1).getParent1().getName(), t, map, level-1);
			getGreatGrandparents(map.get(p1).getParent2().getName(), t, map, level-1);
		}
		return t;
	}
	//Returns list of names of descendants of a person
	public ArrayList<String> getDescendants(String p, ArrayList<String> descendents, Map<String, person> map)
	{
		if(map.get(p).hasChildren()) //Base Case
			return descendents;
		else
			for(int d = 0; d < map.get(p).getChildren().size(); d++)
			{
				getDescendants(map.get(p).getChildren().get(d).getName(), descendents, map);
				descendents.add(map.get(p).getChildren().get(d).getName());
			}
		return descendents;
	}

	//Returns ArrayList of Unrelated of p
	public ArrayList<String> listUnrelated(person p, ArrayList<person> tree, Map<String, person> map, int count)
	{
		ArrayList<String> unrelated = new ArrayList<String>();
		for(int i = count-1; i >= 0; i--) //Iterates through the list of Unrelated
		{
			if(isUnrelated(map.get(i), map.get(p), tree, map, count).equals("Yes"))
				unrelated.add(tree.get(i).getName());
		}
		if(unrelated.isEmpty())
			unrelated.add(map.get(p).getName() + " is related to everyone.");
		return unrelated;
	}





}





