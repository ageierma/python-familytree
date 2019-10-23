/* Connor Hennessey 
 * 9/15/19 
 * Assignment 1 - Family Tree 
 * Organization of Programming Languages 
 */

import java.util.*;

public class person 
{ 
	private String name; 
	private person parent1, parent2; 
	private boolean hasParents = false; 
	private ArrayList<person> children; 
	private ArrayList<person> spouses; 
	
	//Adds a person without parents to the tree 
	public person(String n) 
	{	
		name = n;	
		hasParents = false; 
		children = new ArrayList<person>(); 
		spouses = new ArrayList<person>(); 
	} 
	
	//Adds a person with parents to the tree 
	public person(String n, person p1, person p2) 
	{
		name = n; 
		parent1 = p1; 
		parent2 = p2; 
		hasParents = true; 
		children = new ArrayList<person>(); 
		spouses = new ArrayList<person>(); 
	} 
	
	//Returns name 
	public String getName() 
	{	return name;	} 
	
	//Returns true if person has parents 
	public boolean hasParents() 
	{	return hasParents;	} 
	
	//Returns a person's parent1 
	public person getParent1() 
	{	return parent1;		} 
	
	//Returns a person's parent2 
	public person getParent2() 
	{	return parent2;		} 
	
	//Checks if person has Children
	public boolean hasChildren()
	{	return !this.children.isEmpty();	}
	
	//Keeps a list of a Parent's Children 
	public void addChildren(person p) 
	{	children.add(p);	} 
	
	//Returns ArrayList of Parent's Children 
	public ArrayList<person> getChildren() 
	{	return children;	} 
	
	//Returns list of Children's names 
	public ArrayList<String> getChildrenNames()
	{
		ArrayList<String> names = new ArrayList<String>(); 
		if(!children.isEmpty()) 
			for(int c = 0; c < children.size(); c++) 
				names.add(children.get(c).getName()); 
		return names; 
	}
	
	//Keeps a list of a person's spouses 
	public void setSpouse(person p) 
	{	spouses.add(p);		}
	
	//Returns name of latest spouse 
	public String getSpouse()
	{	
		if(spouses.isEmpty()) 
			return "Single"; 
		else 
			return spouses.get(spouses.size()-1).getName();		
	}
	
	//Checks if person has siblings 
	public boolean hasSiblings()
	{
		if(parent1.hasChildren() || parent2.hasChildren()) 
			return true; 
		else 
			return false; 
	}
	
	//Returns list of person's siblings 
	public ArrayList<String> getSiblings()
	{
		ArrayList<String> siblings = new ArrayList<String>(); 
		for(int p1 = 0; p1 < parent1.getChildren().size(); p1++) 
			siblings.add(parent1.getChildren().get(p1).name); 
		for(int p2 = 0; p2 < parent2.getChildren().size(); p2++) 
			siblings.add(parent2.getChildren().get(p2).name); 
		return siblings; 
	}
} 








