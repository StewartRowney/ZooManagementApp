import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./pages/LayoutPage";
import Home from "./pages/HomePage";
import Contact from "./pages/ContactPage";
import NoPage from "./pages/NoPage";
import Zoos from "./pages/ZoosPage";

export default function App() {

  return (
  
  <BrowserRouter>
    <Routes>
    <Route path="/" element={<Layout />}>
      <Route index element={<Home />} />
      <Route path="contact" element={<Contact />} />
      <Route path="zoos" element={<Zoos />}/>
      <Route path="*" element={<NoPage />} />
    </Route>
  </Routes>
</BrowserRouter>

  );
}
