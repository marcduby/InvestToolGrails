package com.doobs.invest

import java.util.Date;

class Holding implements Comparable {
	Integer id
	Security security
	Account account
	Float amount
	Float purchaseAmount;
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return this.security?.name + " - " + amount
	}

	public Float getTotalAmount() {
		Float total = (security?.currentPrice) ? amount * security?.currentPrice?.price : 0.0
		return total
	}	
	
	public Float getTotalPurchaseAmount() {
		Float total = (this.purchaseAmount) ? amount * purchaseAmount : 0.0
		return total
	}	
	
	public Float getTotalDividend() {
		Float total = (security?.currentPrice) ? amount * security?.currentPrice?.yearlyDividend : 0.0
		return total
	}
	
	public int compareTo(Object object) {
		Holding otherHolding = (Holding)object
	
		if (account?.name == otherHolding?.account?.name) {
			if ((security?.industry == null) || otherHolding?.security?.industry == null) {
				return security?.name?.compareTo(otherHolding?.security?.name)
				
			} else if (security?.industry?.name == otherHolding?.security?.industry?.name) {
				return security?.name?.compareTo(otherHolding?.security?.name)
				
			} else {
				return security?.industry?.name.compareTo(otherHolding?.security?.industry?.name)
			}
			
		} else {
			return account?.name?.compareTo(otherHolding?.account?.name)
		}	
	}
	
    static constraints = {
		security nullable: false
		account nullable: false
    }
	
	static namedQueries = {
		loadByAccountId { Integer accountId ->
			account {
				eq 'id', accountId
			}
			security {
				order("name")
			}
		}
		
		listOrderedByAccountIndustryAndName {
			and {
				account {
					order("name")
				}
				security {
					order("name")
				}
			}
		}
	}
	
	static mapping = {
		table 'inv_holding'
		id 				column: 'holding_id'
		amount		 	column: 'amount'
		purchaseAmount	column: 'purchase_amount'
		security		column: 'security_id', fetch: 'join'
		account			column: 'account_id', fetch: 'join'
	}
}
