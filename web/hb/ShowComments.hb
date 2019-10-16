<div id="ShowComments" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Comments</h4>
            </div>
            <div class="modal-body">
                <table class="table">
                    <tbody>
                        {{#each mData}}
                        <tr>
                            <td>{{this.cText}}</td>
                            <td><button class="ShowComments-user" data-value="{{this.uId}}"></button></td>
                        </tr>
                        {{/each}}
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowComments-Close">Close</button>
            </div>
        </div>
    </div>
</div>