import React from 'react';

interface SortButtonProps {
    onClick: () => void;
}

const SortButton: React.FC<SortButtonProps> = ({ onClick }) => (
    <button className="sort-btn" onClick={onClick} type="button">
        ⇅
    </button>
);

export default SortButton;