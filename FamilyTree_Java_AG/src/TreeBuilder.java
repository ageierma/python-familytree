//code adapted from u/rpajunen on Github.io
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.LinkedList;
public class TreeBuilder {
    public static class FamilyTree implements Iterable<Person> {
        private ArrayList<Person> FamilyTree = null;

        public FamilyTree() {
            this.FamilyTree = new ArrayList<Person>();
        }

        public void addPerson(String name) {
            FamilyTree.add(new Person(name));
        }

        public void addMarriage(String name, String spouse) {
            for (Person p : this.FamilyTree) {
                if (p.name == name) {
                    for (Person s : this.FamilyTree) {
                        if (s.name == spouse) {
                            p.addSpouse(s);
                            s.addSpouse(p);
                        } else {
                            s = new Person(spouse);
                            s.addSpouse(p);
                            p.addSpouse(s);
                        }
                    }
                } else {
                    Person n = new Person(name);
                    this.FamilyTree.add(n);
                    System.out.println("added");
                    for (Person s : this.FamilyTree) {
                        if (s.name == spouse) {
                            n.addSpouse(s);
                            s.addSpouse(n);
                        } else {
                            s = new Person(spouse);
                            s.addSpouse(n);
                            n.addSpouse(s);
                        }
                    }
                }
            }
        }

        public void addFamily(String name, String spouse, String child) {
            for (Person p : this.FamilyTree) {
                if (p.name == name) {
                    for (Person s : this.FamilyTree) {
                        if (s.name == spouse) {
                            for (Person c : this.FamilyTree) {
                                if (c.name == child) {
                                    p.addChildren(c);
                                    s.addChildren(c);
                                    p.addSpouse(s);
                                    s.addSpouse(c);
                                } else {
                                    Person ch = new Person(child);
                                    this.FamilyTree.add(ch);
                                    p.addChildren(ch);
                                    s.addChildren(ch);
                                    p.addSpouse(s);
                                    s.addSpouse(p);
                                }
                            }
                        } else {
                            Person sp = new Person(spouse);
                            this.FamilyTree.add(sp);
                            for (Person c : this.FamilyTree) {
                                if (c.name == child) {
                                    p.addChildren(c);
                                    s.addChildren(c);
                                    p.addSpouse(s);
                                    s.addSpouse(c);
                                } else {
                                    Person ch = new Person(child);
                                    this.FamilyTree.add(ch);
                                    p.addChildren(ch);
                                    s.addChildren(ch);
                                    p.addSpouse(s);
                                    s.addSpouse(p);
                                }
                            }
                        }
                    }
                } else {
                    Person n = new Person(name);
                    this.FamilyTree.add(n);
                    for (Person s : this.FamilyTree) {
                        if (s.name == spouse) {
                            for (Person c : this.FamilyTree) {
                                if (c.name == child) {
                                    p.addChildren(c);
                                    s.addChildren(c);
                                    p.addSpouse(s);
                                    s.addSpouse(c);
                                } else {
                                    Person ch = new Person(child);
                                    this.FamilyTree.add(ch);
                                    p.addChildren(ch);
                                    s.addChildren(ch);
                                    p.addSpouse(s);
                                    s.addSpouse(p);
                                }
                            }
                        } else {
                            Person sp = new Person(spouse);
                            this.FamilyTree.add(sp);
                            for (Person c : this.FamilyTree) {
                                if (c.name == child) {
                                    p.addChildren(c);
                                    s.addChildren(c);
                                    p.addSpouse(s);
                                    s.addSpouse(c);
                                } else {
                                    Person ch = new Person(child);
                                    this.FamilyTree.add(ch);
                                    p.addChildren(ch);
                                    s.addChildren(ch);
                                    p.addSpouse(s);
                                    s.addSpouse(p);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void ancestry(Person p) {
            for (Person q : this.FamilyTree) {
                if (q.hasChildren(q)) {
                    for (Person c : q.getChildren()) {
                        if (c.name == p.name) {
                            p.addAncestor(q);
                            ancestry(c);
                        }
                    }
                }
                if (q.hasSpouse(q)) {
                    for (Person s : q.getSpouses()) {
                        if (s.hasChildren(s)) {
                            for (Person d : s.getChildren()) {
                                if (d.name == p.name) {
                                    p.addAncestor(s);
                                    ancestry(d);
                                }
                            }
                        }
                    }
                }
            }
        }

        /*for(String c : p.getChildren())
        {
            if(c == child)
            {
                ancestors.add(p);
            }
        }*/
        public void sibling(Person p) {
            for (Person q : this.FamilyTree) {
                if (q.hasChildren(q)) {
                    for (Person c : q.getChildren()) {
                        if (c.name == p.name) {
                            for (Person s : q.getChildren()) {
                                p.addSibling(s);
                                s.addSibling(p);
                            }
                        }
                    }
                }
            }
        }

        /*int f = 0;
        Person q = null;
        for(int i = 0; i < this.FamilyTree.size(); i++)
        {
            f = this.FamilyTree.indexOf(p);
        }
        for(int i = 0; i < this.FamilyTree.size(); i++)
        {
            if (i == f)
            {
                q = this.FamilyTree.get(i);
            }
        }
        for(int i = 0; i < this.FamilyTree.size(); i++)
        {
            if(this.FamilyTree.get(i).name == q.name)
            {
                for(int j = 0; j < this.FamilyTree.get(i).getChildren().size(); j++)
                {
                    this.FamilyTree.get(i).getChildren().get(j).addSibling(q);
                    q.addSibling(this.FamilyTree.get(i).getChildren().get(j));
                }
            }
        }
    }*/
        public void cousin(Person p) {
            for (Person q : this.FamilyTree) {
                ancestry(q);
                if (q.hasSibling(q) && q.hasChildren(q)) {
                    for (Person s : q.getSiblings()) {
                        if (s.hasChildren(s)) {
                            for (Person c : s.getChildren()) {
                                for (Person a : p.getAncestors()) {
                                    for (Person an : c.getAncestors()) {
                                        if (an.name == a.name) {
                                            p.addCousin(c);
                                            c.addCousin(p);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public boolean unrelated(Person p) {
            {
                if (p.hasChildren(p) != true) {
                    if (p.hasSpouse(p) != true) {
                        if (p.hasSibling(p) != true) {
                            if (p.hasCousin(p) != true) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        public Iterator<Person> iterator()
        {
            return new Iterator<Person>()
            {
                @Override
                public boolean hasNext()
                {
                    return false;
                }

                @Override
                public Person next()
                {
                    return null;
                }
            };
        }
    }
}
