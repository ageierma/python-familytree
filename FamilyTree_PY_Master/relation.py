class Relation:
    def xMethod(self, name1, name2, relation, degree):
        if relation == 'child':
            if name1 in name2.get_children(name2):
                return 'Yes'
            else:
                return 'No'

        if relation == 'sibling':
            if name2 in name1.get_siblings():
                return 'yes'
            else:
                return 'no'

        if relation == 'ancestor':
            if name1 in self.getAncestors(name2):
                return 'yes'
            else:
                return 'no'

        if relation == 'cousin':
            if self.isCousin(name1, name2) is True:
                return 'yes'
            else:
                return 'no'

        if relation == 'cousin' and degree > -1:
            if self.isCousin2(name1, name2, degree) is True:
                return 'Yes'
            else:
                return 'No'

        if relation == 'unrelated':
            if self.isRelated(name1, name2) is True:
                return 'no'
            else:
                return 'yes'

    def wMethod(self, name1, relation, tree, degree):

        if relation == 'child':
            plist = list()
            for a in name1.get_children():
                plist.append(a.get_name())

            plist.sort()
            return plist

        if relation == 'sibling':
            slist = []
            for a in name1.get_siblings():
                slist.append(a.get_name())

            slist.sort()
            return slist

        if relation == 'ancestor':
            alist = list()
            for a in self.getAncestors(name1):
                if a.get_name() not in alist:
                    alist.append(a.get_name())

            alist.sort()
            return alist

        if relation == 'cousin':
            clist = list()
            for a in self.getCousins(name1, tree):
                if a.get_name() not in clist:
                    clist.append(a.get_name())

            clist.sort()
            return clist

        if relation == 'cousin' and degree > -1:
            clist = list()
            for a in self.getCousins2(name1, tree, degree):
                if a.get_name() not in clist:
                    clist.append(a.get_name())

            clist.sort()
            return clist

        if relation == 'unrelated':
            rlist = list()
            for a in self.getUnrelated(name1, tree):
                if a.get_name() not in rlist:
                    rlist.append(a.get_name())

            rlist.sort()
            return rlist

    def getAncestors(self, name):
        ancestors = list()
        if name is None:
            return []
        if len(name.get_parents()) < 1:
            return []
        else:
            ancestors.append(name.get_parents()[0])
            ancestors.append(name.get_parents()[1])
            ancestors.extend(self.getAncestors(name.get_parents()[0]))
            ancestors.extend(self.getAncestors(name.get_parents()[1]))

        return ancestors

    def isRelated(self, name1, name2):
        result = False
        memberAncestors = self.getAncestors(name1)
        relativeAncestors = self.getAncestors(name2)

        # check if they share common ancestor
        for person1 in memberAncestors:
            for person2 in relativeAncestors:
                if person1 == person2:
                    result = True

        # check if one is a direct ancestor
        for person in memberAncestors:
            if person == name2:
                result = True

        for person in relativeAncestors:
            if person == name1:
                result = True

        return result

    def isCousin(self, name1, name2):
        result = False
        memberAncestors = self.getAncestors(name1)
        relativeAncestors = self.getAncestors(name2)

        # check if they share common ancestor
        for person1 in memberAncestors:
            for person2 in relativeAncestors:
                if person1 == person2:
                    result = True

        # check if one is a direct ancestor
        for person in memberAncestors:
            if person == name2:
                result = False

        for person in relativeAncestors:
            if person == name1:
                result = False

        return result

    def isCousin2(self, name1, name2, degree):  # not finished *************
        result = False
        memberAncestors = self.getAncestors(name1)
        relativeAncestors = self.getAncestors(name2)

        # check if they share common ancestor
        for person1 in memberAncestors:
            for person2 in relativeAncestors:
                if person1 == person2:
                    result = True

        # check if one is a direct ancestor
        for person in memberAncestors:
            if person == name2:
                result = False

        for person in relativeAncestors:
            if person == name1:
                result = False

        return result

    def getUnrelated(self, name, familyTree):
        unRelated = list()

        if name is None:
            return []
        else:
            for person1 in familyTree.values():
                if self.isRelated(person1, name) is False:
                    unRelated.append(person1)
        return unRelated

    def getCousins(self, name, tree):
        if name is None:
            return []
        cousins = list()
        for cousin in tree.values():
            if self.isCousin(name, cousin) and name != cousin:
                    cousins.append(cousin)
        return cousins

    def getCousins2(self, name, tree, degree):  # not finished *************
        if name is None:
            return []
        cousins = list()
        for cousin in tree.values():
            if self.isCousin(name, cousin) and name != cousin:
                    cousins.append(cousin)
        return cousins