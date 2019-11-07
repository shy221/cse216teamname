<div id="ShowPosts" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Posts</h4>
            </div>
            <div class="modal-body">
                <table class="table">
                    <tbody>
                        {{#each mData}}
                        <tr>
                            <td>{{this.mTitle}}</td>
                        </tr>
                        {{/each}}
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowPosts-Close">Close</button>
            </div>
        </div>
    </div>
</div>