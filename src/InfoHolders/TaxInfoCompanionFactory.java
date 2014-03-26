/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infoHolders;

import monopoly.Space;

/**
 *
 * @author jorne
 */
public class TaxInfoCompanionFactory implements InfoCompanionFactory{

    @Override
    public InfoCompanion createInfoCompanion(Space space, String infoprop) {
        return new TaxInfoCompanion(space,infoprop);
    }
    
        @Override
    public String companionType() {
        return "TaxInfo";
    }
}
