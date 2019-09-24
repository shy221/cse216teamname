var describe: any;
var it: any;
var expect: any;
var $: any;

describe("Tests of UI: Add Post feature", function() {

    it("UI Test: Add Button Show Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();        
    });

    it("UI Test: Add Button Show Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();        
    });

});