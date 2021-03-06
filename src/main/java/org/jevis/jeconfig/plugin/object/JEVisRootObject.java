/**
 * Copyright (C) 2009 - 2014 Envidatec GmbH <info@envidatec.com>
 *
 * This file is part of JEConfig.
 *
 * JEConfig is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation in version 3.
 *
 * JEConfig is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * JEConfig. If not, see <http://www.gnu.org/licenses/>.
 *
 * JEConfig is part of the OpenJEVis project, further project information are
 * published at <http://www.OpenJEVis.org/>.
 */
package org.jevis.jeconfig.plugin.object;

import java.util.ArrayList;
import java.util.List;
import org.jevis.api.JEVisAttribute;
import org.jevis.api.JEVisClass;
import org.jevis.api.JEVisDataSource;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisObject;
import org.jevis.api.JEVisRelationship;
import org.jevis.api.JEVisType;
import org.jevis.jeconfig.JEConfig;

/**
 * Fake Root
 *
 * @author fs
 */
public class JEVisRootObject implements JEVisObject {

    private List<JEVisObject> _children = new ArrayList<>();
    private final JEVisDataSource _ds;
    private String _name = "Fake Root";

    public JEVisRootObject(JEVisDataSource ds) throws JEVisException {
        this._ds = ds;
//        _children = _ds.getRootObjects();
        _children = JEConfig.getPreLodedRootObjects();//Workaround, the login will allready preload the classes, the API has to be come more intellegent
        for (JEVisObject rootObj : _children) {
//            System.out.println("RealRoot: " + rootObj + "   class: " + rootObj.getJEVisClass());
        }
    }

    @Override
    public String getName() {
        System.out.println("root.getName: " + _name);
        return _name;
    }

    @Override
    public void setName(String name) throws JEVisException {
        _name = name;
    }

    @Override
    public Long getID() {
        return -1l;
    }

    @Override
    public JEVisClass getJEVisClass() throws JEVisException {
        return null;
    }

    @Override
    public List<JEVisObject> getParents() throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public List<JEVisObject> getChildren() throws JEVisException {
        System.out.println("Root getChildren");
        return _children;
    }

    @Override
    public List<JEVisObject> getChildren(JEVisClass type, boolean inherit) throws JEVisException {
        System.out.println("Root getChildren");
        //TODO implement filter, copy form original sql
        return _children;
    }

    @Override
    public List<JEVisAttribute> getAttributes() throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public JEVisAttribute getAttribute(JEVisType type) throws JEVisException {
        return null;
    }

    @Override
    public JEVisAttribute getAttribute(String type) throws JEVisException {
        return null;
    }

    @Override
    public boolean isAllowedUnder(JEVisObject otherObject) throws JEVisException {
        return false;
    }

    @Override
    public boolean delete() throws JEVisException {
        return false;
    }

    @Override
    public JEVisObject buildObject(String name, JEVisClass type) throws JEVisException {
        return null;
    }

    @Override
    public JEVisObject getLinkedObject() throws JEVisException {
        return null;
    }

    @Override
    public JEVisRelationship buildRelationship(JEVisObject obj, int type, int direction) throws JEVisException {
        return null;
    }

    @Override
    public void deleteRelationship(JEVisRelationship rel) throws JEVisException {
        //
    }

    @Override
    public List<JEVisRelationship> getRelationships() throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public List<JEVisRelationship> getRelationships(int type) throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public List<JEVisRelationship> getRelationships(int type, int direction) throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public List<JEVisClass> getAllowedChildrenClasses() throws JEVisException {
        return new ArrayList<>();
    }

    @Override
    public JEVisDataSource getDataSource() throws JEVisException {
        return _ds;
    }

    @Override
    public void commit() throws JEVisException {
        ;
    }

    @Override
    public void rollBack() throws JEVisException {
        ;
    }

    @Override
    public boolean hasChanged() {
        return false;
    }

    @Override
    public int compareTo(JEVisObject o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "JEVisRootObject{ This Object is a fake and does not exist on the Server }";
    }

//    @Override
//    public JEVisObject buildLink(String name, JEVisObject parent) throws JEVisException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
