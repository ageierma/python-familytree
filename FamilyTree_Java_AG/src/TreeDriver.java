import java.lang.reflect.Array;
import java.util.Scanner;
import java.io.BufferedReader;
import java.util.ArrayList;
public class TreeDriver extends TreeBuilder {
    public static void main(String[] args) {
        FamilyTree f = new FamilyTree();
        TreeDriver driver = new TreeDriver();
        System.out.println("Family Tree Builder");
        System.out.println("Enter a query of the form 'E <1> <2> <3> or X <1> <rel> <2> or W <rel> <1>'. Enter 'ESC' to exit.");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println(f.iterator().next());
            String s = scan.nextLine();
            if (s == "ESC") {
                break;
            } else {
                if (s.charAt(0) == 'E' || s.charAt(0) == 'e') {
                    String[] arr = s.split(" ");
                    if (arr.length == 4) {
                        f.addFamily(arr[1], arr[2], arr[3]);
                        System.out.println("Family of " + arr[1] + ", " + arr[2] + ", " + arr[3] + " added to tree.");
                    } else {
                        f.addMarriage(arr[1], arr[2]);
                        System.out.println("Marriage of " + arr[1] + " and " + arr[2] + " added to tree.");
                    }
                    for (Person print : f) {
                        System.out.println(print.name);
                    }
                }
                if (s.charAt(0) == 'X' || s.charAt(0) == 'x') {
                    for (Person print : f) {
                        System.out.println(print.name);
                    }
                    String[] arr = s.split(" ");
                    for(String str : arr)
                    {
                        System.out.println(str);
                    }
                    if (arr[2] == "ancestor") {
                        for (Person p : f) {
                            f.ancestry(p);
                            if (p.name == arr[1]) {
                                if (p.hasAncestor(p)) {
                                    for (Person a : p.getAncestors()) {
                                        if (a.name == arr[2]) {
                                            System.out.println(arr[1] + " is an ancestor of " + arr[3]);
                                        } else {
                                            System.out.println(arr[1] + " is not an ancestor of " + arr[3]);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (arr[2] == "child") {
                        for (Person p : f) {
                            f.ancestry(p);
                            System.out.println(p.name + "here");
                            if (p.name == arr[3]) {
                                System.out.println(p.name + "here2");
                                if (!p.getChildren().isEmpty()) {
                                    for (Person c : p.getChildren()) {
                                        if (c.name == arr[1]) {
                                            System.out.println(arr[1] + " is an child of " + arr[3]);
                                        } else {
                                            System.out.println(arr[1] + " is not a child of " + arr[3]);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (arr[2] == "sibling") {
                        for (Person p : f) {
                            f.sibling(p);
                            if (p.name == arr[1]) {
                                for (Person sib : p.getSiblings()) {
                                    if (sib.name == arr[3]) {
                                        System.out.println(arr[1] + " is a sibling of " + arr[3]);
                                    } else {
                                        System.out.println(arr[1] + " is not a sibling of " + arr[1]);
                                    }
                                }
                            }
                        }
                    } else if (arr[2] == "cousin") {
                        for (Person p : f) {
                            f.cousin(p);
                            if (p.name == arr[1]) {
                                for (Person c : p.getCousins()) {
                                    if (c.name == arr[3]) {
                                        System.out.println(arr[1] + " is a cousin of " + arr[3]);
                                    } else {
                                        System.out.println(arr[1] + " is not a cousin of " + arr[3]);
                                    }
                                }
                            }
                        }
                    } else if (arr[2] == "unrelated") {
                        for (Person p : f) {
                            f.ancestry(p);
                            f.sibling(p);
                            f.cousin(p);
                            if (p.name == arr[1]) {
                                for (Person a : p.getAncestors()) {
                                    if (a.name == arr[3]) {
                                        System.out.println(arr[1] + " is related to " + arr[3]);
                                    } else {
                                        for (Person sibling : p.getSiblings()) {
                                            if (sibling.name == arr[3]) {
                                                System.out.println(arr[1] + " is related to " + arr[3]);
                                            } else {
                                                for (Person c : p.getCousins()) {
                                                    if (c.name == arr[3]) {
                                                        System.out.println(arr[1] + " is related to " + arr[3]);
                                                    } else {
                                                        System.out.println(arr[1] + " is not related to " + arr[3]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (s.charAt(0) == 'W' || s.charAt(0) == 'w') {
                    String[] arr = s.split(" ");
                    if (arr[2] == "ancestor") {
                        for (Person p : f) {
                            f.ancestry(p);
                            if (p.name == arr[1]) {
                                System.out.println("Ancestors of " + p.name + ": ");
                                for (Person a : p.getAncestors()) {
                                    System.out.println(a.name);
                                }
                            }
                        }
                    }
                    if (arr[2] == "child") {
                        for (Person p : f) {
                            f.ancestry(p);
                            if (p.name == arr[1]) {
                                System.out.println("Children of " + p.name + ": ");
                                for (Person c : p.getChildren()) {
                                    System.out.println(c.name);
                                }
                            }
                        }
                    }
                    if (arr[2] == "sibling") {
                        for (Person p : f) {
                            f.ancestry(p);
                            f.sibling(p);
                            if (p.name == arr[1]) {
                                System.out.println("Siblings of " + p.name + ": ");
                                for (Person sib : p.getSiblings()) {
                                    System.out.println(sib.name);
                                }
                            }
                        }
                    }
                    if (arr[2] == "cousin") {
                        for (Person p : f) {
                            f.ancestry(p);
                            if (p.name == arr[1]) {
                                System.out.println("Cousins of " + p.name + ": ");
                                for (Person cous : p.getCousins()) {
                                    System.out.println(cous.name);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
