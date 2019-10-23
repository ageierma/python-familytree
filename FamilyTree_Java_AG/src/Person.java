//code adapted from u/rpajunen on Github.io

//import java.lang.reflect.Member;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.Objects;

public class Person
{
    //E John Mary Bill
    String name = null;
    private ArrayList<Person> ancestors = null;
    private ArrayList<Person> spouses = null;
    private ArrayList<Person> children = null;
    private ArrayList<Person> siblings = null;
    private ArrayList<Person> cousins = null;

    public Person(String name)
    {
        this.name = name;

        //this.members = new ArrayList<>();
    }

    public String getName()
    {
        return this.name;
    }
    public void addChildren(Person p)
    {
        //ArrayList<Person> children = null;
        children.add(p);
    }
    public void addSpouse(Person p)
    {
        //ArrayList<Person> spouses = null;
        spouses.add(p);
    }
    public void addAncestor(Person p)
    {
        //ArrayList<Person> descendants = null;
        ancestors.add(p);
    }
    public void addSibling(Person p)
    {
        //ArrayList<Person> siblings = null;
        siblings.add(p);
    }
    public void addCousin(Person p)
    {
        //ArrayList<Person> cousins = null;
        cousins.add(p);
    }
    public ArrayList<Person> getSpouses()
    {
        return spouses;
    }
    public ArrayList<Person> getChildren()
    {
        return children;
    }
    public ArrayList<Person> getSiblings()
    {
        return siblings;
    }
    public ArrayList<Person> getCousins()
    {
        return cousins;
    }

    public ArrayList<Person> getAncestors()
    {
        return ancestors;
    }
    public boolean hasChildren(Person p)
    {
        if(p.children != null)
        {
            return true;
        }
        return false;
    }
    public boolean hasSpouse(Person p)
    {
        if(p.spouses != null)
        {
            return true;
        }
        return false;
    }
    public boolean hasSibling(Person p)
    {
        if(p.siblings != null)
        {
            return true;
        }
        return false;
    }
    public boolean hasCousin(Person p)
    {
        if(p.cousins != null)
        {
            return true;
        }
        return false;
    }
    public boolean hasAncestor(Person p)
    {
        if(p.ancestors != null)
        {
            return true;
        }
        return false;
    }
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        final Person p = (Person) o;
        return equals(this.name == p.name);
    }
}
