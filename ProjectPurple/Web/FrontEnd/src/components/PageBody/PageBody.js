import React from 'react';
import { Collapse } from 'reactstrap';

const PageBody = () => {
    return (
        <div>
            <div className="mt-0">
                <ol className="breadcrumb">
                    <li className="breadcrumb-item">
                        <a href="#">장치 정보</a>
                    </li>
                    <li className="breadcrumb-item active">My Dashboard</li>
                </ol>
            </div>

            <div className="card mt-3 mb-2 ml-5 mr-5">
                <div className="list-group list-group-flush small">
                    <a className="list-group-item list-group-item-action" href="#">
                        <div className="media">
                            <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/45x45" alt="" />
                            <div className="media-body">
                                <strong>David Miller</strong>posted a new article to
                                        <strong>David Miller Website</strong>.
                            <div className="text-muted smaller">Today at 5:43 PM - 5m ago</div>
                            </div>
                        </div>
                    </a>
                </div>
                {/* <Collapse isOpen={true}>
                    <div className="card m-2">
                        <div className="list-group list-group-flush small">
                            <a className="list-group-item list-group-item-action" href="#">
                                <div className="media">
                                    <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/45x45" alt="" />
                                    <div className="media-body">
                                        <strong>David Miller</strong>posted a new article to
                                        <strong>David Miller Website</strong>.
                            <div className="text-muted smaller">Today at 5:43 PM - 5m ago</div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </Collapse> */}
            </div>

            <div className="card mt-3 mb-2 ml-5 mr-5">
                <div className="list-group list-group-flush small">
                    <a className="list-group-item list-group-item-action" href="#">
                        <div className="media">
                            <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/45x45" alt="" />
                            <div className="media-body">
                                <strong>David Miller</strong>posted a new article to
                                        <strong>David Miller Website</strong>.
                            <div className="text-muted smaller">Today at 5:43 PM - 5m ago</div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    )
};

export default PageBody;