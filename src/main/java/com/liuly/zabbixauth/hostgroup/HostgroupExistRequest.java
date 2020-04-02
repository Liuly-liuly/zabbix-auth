/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Suguru Yajima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liuly.zabbixauth.hostgroup;

import com.liuly.zabbixauth.common.ZabbixApiRequest;

import java.util.ArrayList;


public class HostgroupExistRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public HostgroupExistRequest() {
        setMethod("hostgroup.exists");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params {

        private ArrayList<Integer> groupid = new ArrayList<Integer>();

        private ArrayList<String> name = new ArrayList<String>();

        private String node;

        private ArrayList<String> nodeids = new ArrayList<String>();

        public Params() {
        }

        public ArrayList<String> getName() {
            return name;
        }

        public void setName(ArrayList<String> name) {
            this.name = name;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public ArrayList<String> getNodeids() {
            return nodeids;
        }

        public void setNodeids(ArrayList<String> nodeids) {
            this.nodeids = nodeids;
        }

        public ArrayList<Integer> getGroupid() {

            return groupid;
        }

        public void setGroupid(ArrayList<Integer> groupid) {
            this.groupid = groupid;
        }
    }
}
