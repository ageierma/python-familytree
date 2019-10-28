class person:
    def __init__(self, x):
        self.name = x
        self.children = []
        self.parents = []

    def get_name(self):
        return self.name

    def add_children(self, kid):
        self.children.append(kid)

    def get_children(self):
        return self.children

    def get_siblings(self):
        if len(self.parents) > 0:
            temp1 = self.parents[0].get_children()
            temp2 = self.parents[1].get_children()
            list1 = []
            for i in temp1:
                if i in temp2:
                    list1.append(i)
            list1 = list(set(list1))
            list1.remove(self)
            return list1
        else:
            return []

    def add_parents(self, par):
        self.parents.append(par)

    def get_parents(self):
        return self.parents