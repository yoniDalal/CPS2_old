
import './customer-coupons.css';
import CustomerCouponsSidebar from './Sidebar/CustomerCouponsSidebar';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import CustomerService from '../../../../../service/CustomerService';


export default function CustomerCoupons() {

    const [coupons, setCoupons] = useState([{
        id: '',
        category: '',
        title: '',
        description: '',
        startDate: '',
        endDate: '',
        amount: '',
        price: '',
        image: '',
    }])


    useEffect(() => {

        CustomerService.getCustomerCoupons().then(
            response => {
                setCoupons(response.data);
            },
            (error) => {
                try {
                    if (error.response.data.string) {
                        return;
                    }
                    if (error.response) {
                        alert("Login expired, please login again.");
                        AutenticationService.logOut();
                    }
                } catch {
                    alert("Servers are down, please try again later.");
                    AutenticationService.logOut();
                }
            }
        )
    }, [])

    const getCoupons = () => {
        if (coupons[0].id === '')
            return
        return (
            coupons.map((interval, index) => {
                return (
                    <tr key={index}>
                        <td className="CustomerCoupons-td" title={interval.category}>{interval.category}</td>
                        <td className="CustomerCoupons-td" title={interval.title}>{interval.title}</td>
                        <td className="CustomerCoupons-td" title={interval.description}>{interval.description}</td>
                        <td className="CustomerCoupons-td" title={interval.startDate}>{interval.startDate}</td>
                        <td className="CustomerCoupons-td" title={interval.endDate}>{interval.endDate}</td>
                        <td className="CustomerCoupons-td" title={interval.amount}>{interval.amount}</td>
                        <td className="CustomerCoupons-td" title={interval.price}>{interval.price}</td>
                        <td className="CustomerCoupons-td" title={interval.image}>
                            <div id="CustomerCoupons-image-td">
                                <div className="CustomerCoupons-image-div" style={{ backgroundImage: "url(" + interval.image + ")" }}>
                                </div>
                            </div>

                        </td>
                    </tr>
                )
            })
        )
    }



    return (
        <div className="CustomerCoupons">
            <CustomerCouponsSidebar setCoupons={setCoupons} />
            <table className="table" id="CustomerCoupons-table">
                <tbody>
                    <tr>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Category">Category</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Title">Title</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Description">Description</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Release Date">Release Date</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Expiration Date">Expiration Date</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Stock">Stock</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Price">Price</th>
                        <th width="12.5%" className="CustomerCoupons-table-th" title="Image">Image</th>
                    </tr>
                    {getCoupons()}
                </tbody>
            </table>

        </div>
    )

}