package analyzer;

import java.util.LinkedList;
import java.util.List;

public class SyntaxTreeNode {
	
	public enum NodeType {
		PROP_LIST, PROP5, PROP4, PROP3, PROP2, PROP1, TOKEN, ERROR
	};

	private ScannerToken token;
	
	private SyntaxTreeNode parent;
	
	private List<SyntaxTreeNode> children = new LinkedList<SyntaxTreeNode>();
	
	private NodeType nodeType = NodeType.ERROR;
	
	public SyntaxTreeNode(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	public SyntaxTreeNode(ScannerToken token) {
		nodeType = NodeType.TOKEN;
		this.token = token;
	}
	
	public List<SyntaxTreeNode> getChildren() {
		return children;
	}

	public void addChild(SyntaxTreeNode node) {
		if (node == null)
			return;
		children.add(node);
		node.parent = this;
	}

	public int numChildren() {
		return children.size();
	}
	
	public void setToken(ScannerToken token) {
		this.token = token;
	}
	
	public ScannerToken getToken() {
		return token;
	}

	public SyntaxTreeNode getParent() {
		return parent;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	public String toString() {
		if (nodeType != NodeType.TOKEN)
			return nodeType.name();
		return nodeType.name() + ": " + token;
	}
	
}
